
uniform vec2 resolution;
uniform vec2 mouse;
uniform vec3 campos;
uniform float time;

const float MAX_DIST = 99999.0;
const int MAX_REF = 8;
vec3 light = normalize(vec3(-0.5,0.75,-1.0));

mat2 rot(float a){
    float s = sin(a);
    float c = cos(a);
    return mat2(c, -s, s, c);}

vec2 sphIntersect(in vec3 ro, in vec3 rd, float ra) {
    float b = dot(ro, rd);
    float c = dot(ro, ro) - ra * ra;
    float h = b * b - c;
    if(h < 0.0) return vec2(-1.0);
    h = sqrt(h);
    return vec2(-b - h, -b + h);
}

vec2 boxIntersection(in vec3 ro, in vec3 rd, in vec3 rad, out vec3 oN)  {
    vec3 m = 1.0 / rd;
    vec3 n = m * ro;
    vec3 k = abs(m) * rad;
    vec3 t1 = -n - k;
    vec3 t2 = -n + k;
    float tN = max(max(t1.x, t1.y), t1.z);
    float tF = min(min(t2.x, t2.y), t2.z);
    if(tN > tF || tF < 0.0) return vec2(-1.0);
    oN = -sign(rd) * step(t1.yzx, t1.xyz) * step(t1.zxy, t1.xyz);
    return vec2(tN, tF);
}

float plaIntersect(in vec3 ro, in vec3 rd, in vec4 p) {
    return -(dot(ro, p.xyz) + p.w) / dot(rd, p.xyz);
}
vec3 getSky(vec3 rd) {
    vec3 col = vec3(0.3, 0.6, 1.0);
    vec3 sun = vec3(0.95, 0.9, 1.0);
    col *= max(0.0, dot(light, vec3(0.0, 0.0, -1.0)));
    sun *= max(0.0, pow(dot(rd, light), 32.0));
    return clamp(sun + col, 0.0, 1.0);
}
vec4 castRay(inout vec3 ro, inout vec3 rd) {
    vec4 col;
    vec2 minIt = vec2(MAX_DIST);
    vec3 n;
    vec2 it;
    vec3 shperePos = vec3(0.0,-1.0,0.0);
    it = sphIntersect(ro-shperePos,rd,1.0);
    if(it.x > 0.0 && it.x < minIt.x) {
        minIt = it;
        vec3 itPos = ro + rd * it.x;
        n = itPos-shperePos;
        col = vec4(1.0, 0.2, 0.1, 0.7);
    }
    shperePos = vec3(5.0, 3.0, -0.25);
    it = sphIntersect(ro - shperePos, rd, 1.5);
    if(it.x > 0.0 && it.x < minIt.x) {
        minIt = it;
        vec3 itPos = ro + rd * it.x;
        n = normalize(itPos - shperePos);
        col = vec4(1.0);
    } 

    vec3 boxN;
    vec3 boxPos = vec3(0.0, 2.0, 0.0);
    it = boxIntersection(ro - boxPos, rd, vec3(1.0), boxN);
    if(it.x > 0.0 && it.x < minIt.x) {
        minIt = it;
        n = boxN;
        col = vec4  (0.3,0.9,0.1,0.6);
    }

    vec3 planeNormal = vec3(0.0, 0.0, -1.0);
    it = vec2(plaIntersect(ro, rd, vec4(planeNormal, 1.0)));
    if(it.x > 0.0 && it.x < minIt.x) {
        minIt = it;
        n = planeNormal;
        col = vec4(0.5,0.5,0.5,0.0);
    }
    if(minIt.x == MAX_DIST) return vec4(-1.0);
    float diffuse = max(0.0, dot(light, n))*0.5 + 0.5 + 0.5;
    float specular = max(0.0,pow(dot(reflect(rd,n),light),32.0)*2.0);
    vec3 shade =vec3(mix(diffuse, specular, 0.5));
    col.rgb *= mix(shade, vec3(1.0), col.a);
    ro += rd * (minIt.x - 0.001);
    rd = reflect(rd, n);
    return col;
}
vec3 traceRay(vec3 ro, vec3 rd) {
    vec3 col = vec3(dot(light, vec3(0.0, 0.0, -1.0)));
    float reflectivity = 1.0;
    for(int i = 0; i < MAX_REF; i++)
    {
        vec4 refCol = castRay(ro, rd);
        if(refCol.x == -1.0) return mix(col, col * getSky(rd), reflectivity);
        vec3 lightDir = light;
        vec3 shadowRo = ro;
        if(castRay(shadowRo, lightDir).x != -1.0) refCol.rgb *= vec3(refCol.a);
        col *= mix(vec3(1.0), refCol.rgb, reflectivity);
        reflectivity *= refCol.a;
    }
    return col;
}

void main(){
	vec2 uv = (gl_FragCoord.xy/resolution.xy-0.5) * resolution/resolution.y;
    vec3 rayOrigin = campos;
    vec3 rayDirection = normalize(vec3(1.0, uv));
    rayDirection.zx *= rot(-mouse.y);
    rayDirection.xy *= rot(mouse.x);
    //light = normalize(vec3(sin(time * 0.2), 0.75, cos(time * 0.2) - 0.95));
    vec3 col = traceRay(rayOrigin, rayDirection);
    col.r = pow(col.r,0.45);
    col.g = pow(col.g,0.45);
    col.b = pow(col.b,0.45);
	gl_FragColor = vec4(col,1.0);
}