/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_labymedia_ultralight_UltralightPlatform */

#ifndef _Included_com_labymedia_ultralight_UltralightPlatform
#define _Included_com_labymedia_ultralight_UltralightPlatform
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    instance
 * Signature: ()Lcom/labymedia/ultralight/UltralightPlatform;
 */
JNIEXPORT jobject JNICALL Java_com_labymedia_ultralight_UltralightPlatform_instance
  (JNIEnv *, jclass);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    setConfig
 * Signature: (Lcom/labymedia/ultralight/config/UltralightConfig;)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_setConfig
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    usePlatformFontLoader
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_usePlatformFontLoader
  (JNIEnv *, jobject);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    usePlatformFileSystem
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_usePlatformFileSystem
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    setFileSystem
 * Signature: (Lcom/labymedia/ultralight/plugin/filesystem/UltralightFileSystem;)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_setFileSystem
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    setGPUDriver
 * Signature: (Lcom/labymedia/ultralight/plugin/render/UltralightGPUDriver;)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_setGPUDriver
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    setGPUDriverPointer
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_setGPUDriverPointer
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    setClipboard
 * Signature: (Lcom/labymedia/ultralight/plugin/clipboard/UltralightClipboard;)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_setClipboard
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_labymedia_ultralight_UltralightPlatform
 * Method:    setLogger
 * Signature: (Lcom/labymedia/ultralight/plugin/logging/UltralightLogger;)V
 */
JNIEXPORT void JNICALL Java_com_labymedia_ultralight_UltralightPlatform_setLogger
  (JNIEnv *, jobject, jobject);

#ifdef __cplusplus
}
#endif
#endif
