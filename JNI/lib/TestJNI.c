#include <string.h>
#include "TestJNI.h"

jstring Java_TestJNI_test(JNIEnv* env, jobject obj){
	return (*env)->NewStringUTF(env,"this is test for jni");
}
