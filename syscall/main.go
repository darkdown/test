package main

import "syscall"

func abort(funcname string, err error) {
	panic(funcname + " failed: " + err.Error())
}

func setting_status(ok uintptr) {
	if uint32(ok) != 0 {
		print("setting ok: ", ok)
	} else {
		print("setting error: ", ok);
	}
}

func sys_func(lib string, funcName string) uintptr {
	h, err := syscall.LoadLibrary(lib)
	if err != nil {
		abort("LoadLibrary", err)
	}
	defer syscall.FreeLibrary(h)
	proc, err := syscall.GetProcAddress(h, funcName)
	if err != nil {
		abort(funcName, err)
	}
	return proc
}

func main() {
	proc := sys_func("kernel32.dll", "GetVersion")
	r, _, _ := syscall.Syscall(proc, 0, 0, 0, 0)
	setting_status(r)
}
