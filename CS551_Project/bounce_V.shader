#version 430	
uniform vec3 uColor;
uniform mat4 mv_matrix;

layout (location = 0 ) in vec3 iPosition;
layout (location = 1) in vec3 iColor;


out vec3 color;

void  main(void)	{	

	
	gl_Position = mv_matrix*vec4(iPosition.x,  iPosition.y, 0.0, 1.0);	
	
	if (uColor[0]<0)
		color=iColor;
	else
	color=uColor;
}


 