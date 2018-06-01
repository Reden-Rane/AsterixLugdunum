#version 330 core

layout (location = 0) out vec4 color;

in vec2 uv;

uniform sampler2D tex;
uniform vec4 colorTint;

uniform float progress;

void main() {
	vec4 textureColor = texture(tex, uv).rgba * colorTint;
	textureColor.r = clamp(textureColor.r, 0, 1);

	if(textureColor.r <= progress) {
	    color = vec4(0, 0, 0, 1);
	} else {
        color = vec4(0, 0, 0, 0);
	}
}