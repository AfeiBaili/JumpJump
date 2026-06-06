#version 450 core

uniform sampler2D atlas;

in vec2 uv;
out vec4 outColor;

void main() {
    outColor = texture(atlas, vec2(uv.x, 1 - uv.y));
}