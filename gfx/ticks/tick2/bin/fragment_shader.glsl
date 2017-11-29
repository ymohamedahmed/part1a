#version 140

in vec3 frag_normal;	    // fragment normal in world space
in vec2 frag_texcoord;		// fragment texture coordinates in texture space

out vec3 colour;

uniform sampler2D tex;  // 2D texture sampler

void main()
{
	const vec3 I_a = vec3(0.2, 0.2, 0.2);       // Ambient light intensity (and colour)

	const float k_d = 0.8;                      // Diffuse light factor
	vec4 texcolour = texture(tex, frag_texcoord);
    vec3 C_diff = texcolour.rgb;    // Diffuse light colour (TODO: replace with texture)

	const vec3 I = vec3(0.941, 0.968, 1);   // Light intensity (and colour)
	vec3 L = normalize(vec3(2, 1.5, -0.5)); // The light direction as a unit vector
	vec3 N = frag_normal;                   // Normal in world coordinates

	// TODO: Calculate colour using the illumination model
	vec3 amb = C_diff * I_a;
	vec3 diffuse = C_diff * k_d * I * max(0, dot(N,L));
    colour = vec3(amb+diffuse); // TODO: replace this line




}
