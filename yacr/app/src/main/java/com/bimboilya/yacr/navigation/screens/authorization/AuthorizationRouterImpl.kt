package com.bimboilya.yacr.navigation.screens.authorization

import com.bimboilya.common.navigation.AppRouter
import com.bimboilya.yacr.feature.authorization.presentation.AuthorizationRouter
import javax.inject.Inject

class AuthorizationRouterImpl @Inject constructor(
    private val appRouter: AppRouter,
) : AuthorizationRouter {

    override fun openHome() {
        appRouter.replace(AuthDirection.route) // todo change
    }
}