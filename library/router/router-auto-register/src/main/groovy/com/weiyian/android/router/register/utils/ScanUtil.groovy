package com.weiyian.android.router.register.utils


import com.weiyian.android.router.register.core.RegisterTransform
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile

class ScanUtil {

    static void scanJarFile(File inputFile, File outputFile) {
        if (inputFile) {
            def file = new JarFile(inputFile)
            Enumeration enumeration = file.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()

                // [com/alibaba/android/arouter/routes/]
                if (entryName.startsWith(RegisterExtension.ROUTER_CLASS_PACKAGE_NAME)) {
                    InputStream inputStream = file.getInputStream(jarEntry)
                    registerInterfaces(inputStream)

                    Logger.e('[ScanUtil] [scanJarFile] [entryName start with com/alibaba/android/arouter/routes/ ] entryName = ' + entryName)

                    inputStream.close()
                    // [com/alibaba/android/arouter/core/LogisticsCenter]
                } else if (RegisterExtension.GENERATE_TO_CLASS_FILE_NAME == entryName) {
                    // mark this jar file contains LogisticsCenter.class
                    // After the scan is complete, we will generate register code into this file
                    Logger.e('[ScanUtil] [scanJarFile] [entryName LogisticsCenter ] entryName = ' + entryName)
                    RegisterTransform.fileContainsInitClass = outputFile
                }
            }
            file.close()
        }
    }

    static boolean shouldProcessPreDexJar(String path) {
        return !path.contains("com.android.support") && !path.contains("/android/m2repository")
    }

    static boolean shouldProcessClass(String entryName) {
        return entryName != null && entryName.startsWith(RegisterExtension.ROUTER_CLASS_PACKAGE_NAME)
    }

    static void registerInterfaces(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        RegisterClassVisitor cv = new RegisterClassVisitor(Opcodes.ASM5, cw)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        inputStream.close()
    }

    static class RegisterClassVisitor extends ClassVisitor {

        RegisterClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
            // com/alibaba/android/arouter/facade/template/IRouteRoot
            // com/alibaba/android/arouter/facade/template/IRouteGroup
            // com/alibaba/android/arouter/facade/template/IInterceptorGroup
            RegisterTransform.registerList.each { ext ->
                if (ext.interfaceName && interfaces != null) {
                    interfaces.each { interfaceName ->
                        Logger.e('[ScanUtil] [visit] interfaceName = ' + interfaceName)
                        if (interfaceName == ext.interfaceName) {
                            ext.registerList.add(name)
                        }
                    }
                }
            }
        }
    }

}