// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("golmansanch");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("golmansanch")
//      }
//    }
#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_goldmansanch_util_Keys_apodKey(JNIEnv *env, jobject thiz) {
    std::string api_key = "eXlXPaeIHhKzEVKtjsLEn5zBlDnScEpMgh17iisv";
    return env->NewStringUTF(api_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_goldmansanch_util_Keys_baseURL(JNIEnv *env, jobject thiz) {
    std::string base_url = "https://api.nasa.gov";
    return env->NewStringUTF(base_url.c_str());
}