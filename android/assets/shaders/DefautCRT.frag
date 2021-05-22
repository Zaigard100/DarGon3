uniform float iTime;
uniform sampler2D u_texture;
uniform vec2 iResolution;

vec2 curve(vec2 uv)
{
	uv = (uv - 0.5) * 2.0;
	uv *= 1.1;
	uv.x *= 1.0 + pow((abs(uv.y) / 5.0), 2.0);
	uv.y *= 1.0 + pow((abs(uv.x) / 4.0), 2.0);
	uv  = (uv / 2.0) + 0.5;
	uv =  uv *0.92 + 0.04;
	return uv;
}

void main()
{
    //Curve
    vec2 uv = gl_FragCoord.xy / iResolution.xy;
	uv = curve( uv );

    vec3 col;

    // Chromatic
    col.r = texture(u_texture,vec2(uv.x+0.003,uv.y)).x;
    col.g = texture(u_texture,vec2(uv.x+0.000,uv.y)).y;
    col.b = texture(u_texture,vec2(uv.x-0.003,uv.y)).z;

    col *= step(0.0, uv.x) * step(0.0, uv.y);
    col *= 1.0 - step(1.0, uv.x) * 1.0 - step(1.0, uv.y);

    col *= 0.5 + 0.5*16.0*uv.x*uv.y*(1.0-uv.x)*(1.0-uv.y);
    col *= vec3(0.95,1.05,0.95);

    col *= 0.9+0.1*sin(10.0*iTime+uv.y*700.0);

    col *= 0.99+0.01*sin(110.0*iTime);

    gl_FragColor = vec4(col,1.0);
}