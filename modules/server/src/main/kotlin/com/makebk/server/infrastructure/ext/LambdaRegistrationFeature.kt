package com.makebk.server.infrastructure.ext

import com.makebk.server.Application
import org.graalvm.nativeimage.hosted.Feature
import org.graalvm.nativeimage.hosted.Feature.DuringSetupAccess
import org.graalvm.nativeimage.hosted.RuntimeSerialization


class LambdaRegistrationFeature : Feature {
    override fun duringSetup(access: DuringSetupAccess?) {
        RuntimeSerialization.registerLambdaCapturingClass(Application::class.java)
    }
}