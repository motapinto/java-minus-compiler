.class public myClass4
.super java/lang/Object

	.method public <init>()V
		aload_0
		invokenonvirtual java/lang/Object/<init>()V
		return
	.end method

	.method public myClass()V
		.limit stack 99
		.limit locals 99
		aload_0
		invokespecial myClass4.<init>()V
		return
	.end method

	.method public check([III)Z
		.limit stack 99
		.limit locals 99
		iconst_0
		istore 4
		iconst_0
		istore 5
		iload 4
		iload_2
		if_icmplt
		iconst_0		istore 6
		iload 5
		ireturn
	.end method

