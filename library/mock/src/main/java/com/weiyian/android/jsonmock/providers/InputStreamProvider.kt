package com.weiyian.android.jsonmock.providers

import java.io.InputStream

/**
 * @author :
 */
interface InputStreamProvider {

    /**
     * provide
     *
     * @param path :
     * @return :
     */
    fun provide(path: String): InputStream?

}
