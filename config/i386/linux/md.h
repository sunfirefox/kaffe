/*
 * i386/linux/md.h
 * Linux i386 configuration information.
 *
 * Copyright (c) 1996, 1997
 *	Transvirtual Technologies, Inc.  All rights reserved.
 *
 * Copyright (c) 2003
 *      Kaffe.org contributors. See ChangeLog for details.
 *
 * See the file "license.terms" for information on usage and redistribution 
 * of this file. 
 */

#ifndef __ki386_linux_md_h
#define __ki386_linux_md_h

#include "i386/common.h"
#include "i386/sysdepCallMethod.h"
#include "i386/threads.h"

/**/
/* Extra exception handling information. */
/**/
#if defined(HAVE_FEATURES_H)
#include <features.h>
#endif
#if defined(HAVE_ASM_SIGNAL_H) && !defined(__GLIBC__)
#include <asm/signal.h>
#endif
#if defined(HAVE_SIGNAL_H)
#include <signal.h>
#endif
#if defined(HAVE_ASM_SIGCONTEXT_H) && !defined(__GLIBC__)
#include <asm/sigcontext.h>
#endif
#if defined(HAVE_SIGCONTEXT_H)
#include <sigcontext.h>
#endif
#if defined(HAVE_SYS_RESOURCE_H)
#include <sys/resource.h>
#endif
#if defined(HAVE_UNISTD_H)
#include <unistd.h>
#endif

/*
 * newer Linux kernel actually implement SA_SIGINFO.
 * But we don't need it, so let's turn it off
 */
#if defined(SA_SIGINFO)
#undef SA_SIGINFO
#endif

/* Linux requires a little initialisation */
extern void init_md(void);
#define	INIT_MD()	init_md()

/* Function prototype for signal handlers */
#if defined(HAVE_STRUCT_SIGCONTEXT_STRUCT) && !defined(__GLIBC__)
/* Linux < 2.1.1 */
#define	SIGNAL_ARGS(sig, sc)						\
	int sig, struct sigcontext_struct sc
#define SIGNAL_CONTEXT_POINTER(scp) struct sigcontext_struct * scp

#elif defined(HAVE_STRUCT_SIGCONTEXT) || defined(__GLIBC__)
/* Linux >= 2.1.1  or Linux 2.0.x with glibc2 */
#define	SIGNAL_ARGS(sig, sc)						\
	int sig, struct sigcontext sc
#define SIGNAL_CONTEXT_POINTER(scp) struct sigcontext * scp
#else
#error Do not know how to define SIGNAL_ARGS
#endif

#define GET_SIGNAL_CONTEXT_POINTER(sc) (&sc)
#define SIGNAL_PC(scp) (scp)->eip
#define STACK_POINTER(scp) (scp)->esp

#if defined(HAVE_GETRLIMIT)
#define KAFFEMD_STACKSIZE

static inline rlim_t mdGetStackSize(void)
{
  struct rlimit rl;

  // The soft limit is always the lower limit.
  // Use it by default.
  if (getrlimit(RLIMIT_STACK, &rl) < 0)
    return 0;
  else
    return rl.rlim_cur;
}
#endif

#if defined(__GLIBC__)
#define KAFFEMD_STACKBASE
extern void *mdGetStackBase(void);
#endif

#if defined(TRANSLATOR)
#include "jit-md.h"
#endif

#endif
