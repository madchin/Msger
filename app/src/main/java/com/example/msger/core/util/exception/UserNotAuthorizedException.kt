package com.example.msger.core.util.exception

class UserNotAuthorizedException(override val message: String = "User is not authorized" ) : Exception()