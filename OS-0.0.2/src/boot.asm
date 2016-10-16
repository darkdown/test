%define _BOOT_DEBUG_

%ifdef _BOOT_DEBUG_
	org 0100h
%else
	org 07c00h
%endif

mov ax, cs
mov ds, ax
mov es, ax
call DispStr
jmp $

DispStr:
	mov ax, BootMessage
	mov bp, ax    ; ES:BP = BootMessage Address
	mov cx, 16    ; CX = BootMessage length
	mov ax, 01301h
	;mov ah, 13h
	;mov al, 01h
	mov bx, 000ch
	;mov bh, 0    ;set page size to 0 (BH = 0) 
	;mov bl, 0ch    ;font-color:red(highlight), background-color: black (BL = 0Ch)
	mov dl, 0
	int 10h
	ret

BootMessage: db "Hello, OS world!"
times 510-($-$$) db 0
dw 0xaa55
