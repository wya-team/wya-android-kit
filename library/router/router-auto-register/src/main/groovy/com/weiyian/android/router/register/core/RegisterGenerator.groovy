package com.weiyian.android.router.register.core

import com.weiyian.android.router.register.utils.Logger
import com.weiyian.android.router.register.utils.RegisterExtension
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.MethodVisitor
import org.apache.commons.io.IOUtils
import org.objectweb.asm.*

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class RegisterGenerator {

    RegisterExtension mExtension

    void insertInitCodeTo(RegisterExtension extension) {
        this.mExtension = extension
        if (extension == null || extension.registerList.isEmpty()) {
            return
        }
        File file = RegisterTransform.fileContainsInitClass
        if (file.getName().endsWith('.jar')) {
            insertInitCodeIntoJarFile(file)
        }
    }

    private File insertInitCodeIntoJarFile(File jarFile) {
        if (jarFile) {
            def optJar = new File(jarFile.getParent(), jarFile.name + ".opt")

            Logger.e("[RegisterGenerator] [insertInitCodeIntoJarFile] optJar = " + optJar)
            if (optJar.exists()) {
                optJar.delete()
            }
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar))

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream inputStream = file.getInputStream(jarEntry)
                jarOutputStream.putNextEntry(zipEntry)
                if (RegisterExtension.GENERATE_TO_CLASS_FILE_NAME == entryName) {
                    Logger.i('Insert init code to class >> ' + entryName)
                    def bytes = referHackWhenInit(inputStream)
                    jarOutputStream.write(bytes)
                } else {
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                inputStream.close()
                jarOutputStream.closeEntry()
            }

            jarOutputStream.close()
            file.close()

            if (jarFile.exists()) {
                jarFile.delete()
            }
            optJar.renameTo(jarFile)
        }
        return jarFile
    }

    private byte[] referHackWhenInit(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ClassVisitor cv = new RouterClassVisitor(Opcodes.ASM5, cw)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        return cw.toByteArray()
    }

    class RouterClassVisitor extends ClassVisitor {

        RouterClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
        }

        @Override
        MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions)
            // loadRouterMap()
            if (name == RegisterExtension.GENERATE_TO_METHOD_NAME) {
                mv = new LoadRouterMapMethodVisitor(Opcodes.ASM5, mv)
            }
            return mv
        }
    }

    class LoadRouterMapMethodVisitor extends MethodVisitor {

        LoadRouterMapMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv)
        }

        @Override
        void visitInsn(int opcode) {
            // generate code before return
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN)) {
                // { com/alibaba/android/arouter/facade/template/IRouteRoot, -> com.alibaba.android.arouter.facade.template.IRouteRoot
                // com/alibaba/android/arouter/facade/template/IInterceptorGroup, -> com.alibaba.android.arouter.facade.template.IInterceptorGroup
                // com/alibaba/android/arouter/facade/template/IProviderGroup } -> com.alibaba.android.arouter.facade.template.IProviderGroup
                mExtension.registerList.each { name ->
                    name = name.replaceAll("/", ".")
                    mv.visitLdcInsn(name) // 类名
                    // generate invoke register method into com/alibaba/android/arouter/core/LogisticsCenter.loadRouterMap()
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, RegisterExtension.GENERATE_TO_CLASS_NAME, RegisterExtension.REGISTER_METHOD_NAME, "(Ljava/lang/String;)V", false)
                }
            }
            super.visitInsn(opcode)
        }

        @Override
        void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(maxStack + 4, maxLocals)
        }
    }
}