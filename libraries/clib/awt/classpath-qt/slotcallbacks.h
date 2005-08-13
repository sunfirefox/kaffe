#ifndef SLOTCALLBACKS_H
#define SLOTCALLBACKS_H

#include <QAbstractButton>
#include <QAbstractSlider>
#include <QAction>
#include <QComboBox>
#include <QPushButton>
#include <QScrollBar>
#include <jni.h>

void connectButton(QPushButton *button, JNIEnv *env, jobject buttonobj);
void connectChoice(QComboBox *choice, JNIEnv *env, jobject choiceobj);
void connectAction(QAction *action, JNIEnv *env, jobject obj);
void connectToggle(QAbstractButton *action, JNIEnv *env, jobject obj);
void connectScrollBar(QScrollBar *scroll, JNIEnv *env, jobject obj);

#endif
