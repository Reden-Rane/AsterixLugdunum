#version 330 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec2 textureUV;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec2 atlasSize;
uniform vec2 spriteCoords;
uniform vec2 spriteSize;

out vec2 uv;

void main() {
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(vertexPosition, 1.0);
    uv = (spriteCoords + textureUV * spriteSize) / atlasSize;
}
