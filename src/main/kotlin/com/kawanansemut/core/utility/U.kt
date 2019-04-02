package com.kawanansemut.core.utility

import java.util.*

class U {
    companion object {
        fun generateSlimUUID() = UUID.randomUUID().toString().replace("-", "")
    }
}