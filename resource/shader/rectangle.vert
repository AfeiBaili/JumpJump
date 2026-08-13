#version 450 core

layout (location = 0) in vec4 inRect;

uniform mat4 projection;
uniform mat4 view;

void main() {

    vec2 pos;

    switch (gl_VertexID) {
        case 0: pos = vec2(inRect.x, inRect.y);
                break;
        case 1: pos = vec2(inRect.x + inRect.z, inRect.y);
                break;
        case 2: pos = vec2(inRect.x + inRect.z, inRect.y + inRect.w);
                break;
        case 3: pos = vec2(inRect.x, inRect.y + inRect.w);
                break;
    }

    gl_Position = projection * view * vec4(pos, 0.0, 1.0);
}