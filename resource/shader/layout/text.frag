#version 450 core

uniform sampler2D atlas;

out vec4 outColor;
in vec2 uv;
in vec4 color;
in vec4 backgroundColor;

void main() {
    vec4 textColor = texture(atlas, vec2(uv.x, uv.y));
    outColor = mix(backgroundColor, color, textColor.a);
}