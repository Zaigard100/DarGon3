uniform float iTime;
uniform sampler2D u_texture;
uniform vec2 iResolution;
void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = gl_FragCoord.xy/iResolution.xy;

    // Time varying pixel color
    vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0, 2, 4));

    float stheta = sin(0.1 * iTime);
    float ctheta = cos(0.1 * iTime);
    vec2 rot_uv = mat2(ctheta, stheta, -stheta, ctheta) * uv;

    vec2 warped_uv = uv + vec2(0.25 * sin(3.0 * rot_uv.x), 0.05 * cos(4.5 * rot_uv.y));

    float wiggle = sin(400.0 * dot(vec2(0.6, 0.8), warped_uv));

    float intense = dot(vec3(0.58), textureLod(u_texture, vec2(1.0 - uv.x, uv.y), 2.0).rgb);

    // Output to screen
    gl_FragColor = vec4(col * clamp(wiggle - 1.0 + 2.0 * intense, 0.0, 1.0), 1.0);
}