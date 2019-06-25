package com.weiyian.android.router.register.core

import com.weiyian.android.router.register.utils.Logger
import com.weiyian.android.router.register.utils.RegisterExtension
import com.weiyian.android.router.register.utils.ScanUtil
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

class RegisterTransform extends Transform {

    static ArrayList<RegisterExtension> registerList
    static File fileContainsInitClass

    RegisterTransform() {
    }

    @Override
    String getName() {
        return RegisterExtension.PLUGIN_NAME
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }


    @Override
    void transform(Context context,
                   Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {
        Logger.e('[RegisterTransform] [transform] start')

        long startTime = System.currentTimeMillis()
        boolean leftSlash = File.separator == '/'


        inputs.each { TransformInput input ->
            handleJarInputs(input, outputProvider)
            handleDirectoryInputs(input, outputProvider, leftSlash)
        }

        Logger.e('[RegisterTransform] [transform] finish , cost time = ' + (System.currentTimeMillis() - startTime) + "ms")

        if (fileContainsInitClass) {
            registerList.each { extension ->
                Logger.i('Insert register code to file ' + fileContainsInitClass.absolutePath)

                if (!extension.registerList.isEmpty()) {
                    extension.registerList.each {
                        Logger.i(it)
                    }
                    new RegisterGenerator().insertInitCodeTo(extension)
                } else {
                    Logger.e("No class implements found for interface:" + extension.interfaceName)
                }
            }
        }

        Logger.i("Generate code finish, current cost time: " + (System.currentTimeMillis() - startTime) + "ms")
    }


    private void handleJarInputs(TransformInput input, TransformOutputProvider outputProvider) {
        input.jarInputs.each { JarInput jarInput ->
            String destName = jarInput.name
            def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
            if (destName.endsWith(".jar")) {
                destName = destName.substring(0, destName.length() - 4)
            }

            File inputFile = jarInput.file
            File outputFile = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
            Logger.e('[RegisterTransform] [transform] [jarInput] inputFile = ' + inputFile + ' , outputFile = ' + outputFile)
            if (ScanUtil.shouldProcessPreDexJar(inputFile.absolutePath)) {
                ScanUtil.scanJarFile(inputFile, outputFile)
            }
            FileUtils.copyFile(inputFile, outputFile)
        }
    }

    private void handleDirectoryInputs(TransformInput input, TransformOutputProvider outputProvider, boolean leftSlash) {
        input.directoryInputs.each { DirectoryInput directoryInput ->
            File outputFile = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)

            String root = directoryInput.file.absolutePath
            if (!root.endsWith(File.separator)) {
                root += File.separator
            }

            directoryInput.file.eachFileRecurse { File file ->
                def path = file.absolutePath.replace(root, '')
                if (!leftSlash) {
                    path = path.replaceAll("\\\\", "/")
                }
                if (file.isFile() && ScanUtil.shouldProcessClass(path)) {
                    ScanUtil.registerInterfaces(new FileInputStream(file))
                }
            }
            Logger.e('[RegisterTransform] [transform] [directoryInputs] outputFile = ' + outputFile)
            FileUtils.copyDirectory(directoryInput.file, outputFile)
        }
    }
}
