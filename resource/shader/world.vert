#version 450 core

layout (location = 0) in vec2 inPos;
layout (location = 1) in vec2 inModelInstance;
layout (location = 2) in vec4 inUv;

out vec2 uv;

uniform mat4 view;
uniform mat4 projection;

void main() {
    gl_Position = projection * view * vec4(inPos + inModelInstance, 0, 1);
    uv = vec2(
    mix(inUv.x, inUv.z, inPos.x),
    mix(inUv.w, inUv.y, inPos.y)
    );
}