#version 430

in vec3 color;
out vec4 fColor; 

void main(void) { 
fColor = vec4(color,1.0); 		
}