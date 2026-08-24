#version 450 core

layout (location = 0) in vec2 inPos;
layout (location = 1) in vec2 inUv;
layout (location = 2) in vec4 inColor;
layout (location = 3) in vec4 inBackgroundColor;

uniform mat4 projection;
uniform mat4 view;

out vec2 uv;
out vec4 color;
out vec4 backgroundColor;

void main() {
    gl_Position = projection * view * vec4(inPos, 0, 1);
    uv = inUv;
    color = inColor;
    backgroundColor = inBackgroundColor;
}