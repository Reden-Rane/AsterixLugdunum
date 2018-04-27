#version 330 core

layout (location = 0) out vec4 color;

in vec2 uv;

uniform sampler2D tex;
uniform vec4 colorTint;

void main() {
	color = texture(tex, uv) * colorTint;
}