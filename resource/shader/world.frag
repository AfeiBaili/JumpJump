#version 450 core

uniform sampler2D atlas;

in vec2 uv;
out vec4 outColor;
uniform float light;

void main() {
    vec4 color = texture(atlas, vec2(uv.x, uv.y));
    outColor = vec4(color.r * light, color.g * light, color.b * light, color.a);
}