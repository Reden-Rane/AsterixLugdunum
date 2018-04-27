#version 330 core

layout (location = 0) out vec4 color;
uniform vec4 colorTint;

uniform vec2 circleCenter;
uniform float circleRadius;

void main() {
    float dist = length(gl_FragCoord.xy - circleCenter);
    float alpha = smoothstep(circleRadius-1.0, circleRadius+1.0, dist);
    color = vec4(colorTint.rgb, alpha);
}