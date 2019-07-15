package com.weiyian.android.router.register.plugin

import com.weiyian.android.router.register.core.RegisterTransform
import com.weiyian.android.router.register.utils.Logger
import com.weiyian.android.router.register.utils.RegisterExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class RegisterPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        // apply plugin: 'com.android.application'
        def isPluginApp = project.plugins.hasPlugin(AppPlugin)
        if (isPluginApp) {
            Logger.make(project)

            def transformImpl = new RegisterTransform()

            ArrayList<RegisterExtension> list = new ArrayList<>(3)
            list.add(new RegisterExtension('IRouteRoot'))
            list.add(new RegisterExtension('IInterceptorGroup'))
            list.add(new RegisterExtension('IProviderGroup'))
            RegisterTransform.registerList = list

            def android = project.extensions.getByType(AppExtension)
            android.registerTransform(transformImpl)
        }
    }

}
