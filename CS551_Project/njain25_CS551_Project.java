import java.awt.Font;
import com.jogamp.opengl.*;
import java.nio.FloatBuffer;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import static com.jogamp.opengl.GL.GL_POINTS;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;
import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_LINES;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.awt.TextRenderer;
import static com.jogamp.opengl.GL2ES3.GL_COLOR;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

public class njain25_CS551_Project extends JOGL2_0_2DTransform {
	float rad = HEIGHT / 3.0f;
	float Rad = 2 * rad / HEIGHT;
	float dpt = 1 / rad;
	float t1 = 10.0f;
	float t2 = 0.0f;
	float t3 = -10.0f;
	int flg;
	int np = (int) (2 * Math.PI / dpt);
	float po[] = new float[np * 3];
	float cr[] = { 0.5f, 0.0f, 1.0f };
	int z = 1000;
	float pIn[] = new float[z * 3];
	float dt[] = new float[z * 3];
	float pIn1[] = new float[z * 3];
	float dt1[] = new float[z * 3];
	float pIn2[] = new float[z * 3];
	float dt2[] = new float[z * 3];
	float clr[] = new float[z * 3];
	TextRenderer rdr;

	public njain25_CS551_Project() {

		for (int i = 0; i < 3 * np; i++) {
			t1 = t1 + dpt;
			po[i] = (float) (Rad * Math.cos(t1));
			i++;
			po[i] = (float) (Rad * Math.sin(t1));
			i++;
			po[i] = 0;
		}

		for (int i = 0; i < z; i = i + 3) {
			pIn[i] = (float) (Rad * (Math.random() - 0.5));
			pIn[i + 1] = (float) (Rad * (Math.random() - 0.5));
			pIn[i + 2] = 0;

			dt[i + 0] = (float) (Math.random() - 0.5) / 100f;
			dt[i + 1] = (float) (Math.random() - 0.5) / 100f;
			dt[i + 2] = 0;

			clr[i + 0] = (float) Math.random();
			clr[i + 1] = (float) Math.random();
			clr[i + 2] = (float) Math.random();
		}

	}

	public void display(GLAutoDrawable drawable) {
		float bgColor[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		FloatBuffer bgColorBuffer = Buffers.newDirectFloatBuffer(bgColor);
		gl.glViewport((WIDTH - HEIGHT) / 2, 0, HEIGHT, HEIGHT);
		gl.glClearBufferfv(GL_COLOR, 0, bgColorBuffer);
		myLoadIdentity();
		uploadMV();
		drawCircle();
		drawPoints();
		float cColor[] = { 0f, 1f, 0.5f };
		cnt += flg;
		if (cnt >= 500)
			flg = -1;
		if (cnt <= 0)
			flg = 1;
		myPushMatrix();
		myRotatef(-((float) cnt) / 20, 0, 0, 1);
		myTranslatef(0.2f, 0f, 0f);
		myScalef(0.1f, 0.1f, 1f);
		uploadMV();
		char_func('J', cColor);
		myPopMatrix();
		myPushMatrix();
		if (cnt % 200 < 100)
			myRotatef((float) cnt / 5f, 0, 0, 1);
		else
			myRotatef((float) -cnt / 5f, 0, 0, 1);
		myScalef(0.04f, 0.04f, 1f);
		uploadMV();
		char_func('X', cColor);
		myPopMatrix();
		myPushMatrix();
		myRotatef(-((float) cnt )/ 20, 0, 0, 1);
		myTranslatef(-0.2f, 0f, 0f);
		myScalef(0.1f, 0.1f, 1f);
		uploadMV();
		cColor[0] = 0;
		cColor[1] = 1;
		char_func('N', cColor);
		myPopMatrix();
		t1 = dpt * flg;
		uploadMV();
		drawTriangle(t1);
	}
	
	public void drawCircle() {
		float vta0[] = { -WIDTH / 4, -WIDTH / 4, -WIDTH / 4 }, vta1[] = { WIDTH / 4, 0, WIDTH / 4 }, vtx2[] = { WIDTH / 4, HEIGHT / 4, 0 };
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vBuf = Buffers.newDirectFloatBuffer(po);
		gl.glBufferData(GL_ARRAY_BUFFER, vBuf.limit() * Float.BYTES, vBuf, GL_STATIC_DRAW);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); 
		gl.glEnableVertexAttribArray(1);
		FloatBuffer cBuf = Buffers.newDirectFloatBuffer(cr);
		int posLoc = gl.glGetUniformLocation(vfPrograms, "uColor");
		gl.glProgramUniform3fv(vfPrograms, posLoc, 1, cBuf);
		gl.glPointSize(2.0f);
		gl.glDrawArrays(GL_POINTS, 0, np);
		gl.glDrawArrays(GL_POINTS, 0, 1);
		if (pos > 0.1f)
			gl.glDrawArrays(GL_LINE_LOOP, 0, 3);
		else if (pos < -0.1f)
			gl.glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	public void drawTriangle(double lth) {
		float cr[] = { 0.0f, 1.0f, 1.0f };
		float tgle[] = new float[6];
		tgle[0] = (float) (Rad * Math.cos(lth));
		tgle[1] = (float) (Rad * Math.sin(lth));
		tgle[2] = (float) (Rad * Math.cos(lth + 2 * Math.PI / 3));
		tgle[3] = (float) (Rad * Math.sin(lth + 2 * Math.PI / 3));
		tgle[4] = (float) (Rad * Math.cos(lth + 4 * Math.PI / 3));
		tgle[5] = (float) (Rad * Math.sin(lth + 4 * Math.PI / 3));
		uploadColor(cr);
		myPushMatrix();
		myRotatef((float) cnt / 50, 0, 0, 1);
		uploadMV();
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vBuf = Buffers.newDirectFloatBuffer(tgle); 
		gl.glBufferData(GL_ARRAY_BUFFER, vBuf.limit() * Float.BYTES, vBuf, GL_STATIC_DRAW); 
		gl.glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); 
		gl.glDisableVertexAttribArray(1);
		gl.glPointSize(2.0f);
		gl.glDrawArrays(GL_LINE_LOOP, 0, 3);
		myPopMatrix();
		cr[0] = 1;
		cr[1] = 0;
		cr[2] = 0;
		uploadColor(cr);
		gl.glPointSize(10.0f);
		gl.glDrawArrays(GL_POINTS, 0, 3);

		myPushMatrix();
		myRotatef((float) cnt / 50, 0, 0, 1);
		myTranslatef(tgle[0] * 1.1f, tgle[1] * 1.1f, 0f);
		myScalef(0.041f, 0.041f, 1f);
		uploadMV();
		myRotatef(-((float) cnt) / 50, 0, 0, 1);
		myTranslatef(0.3f, 0f, 0f);
		uploadMV();
		char_func('X', cr);
		myPopMatrix();

		myPushMatrix();
		myRotatef((float) cnt / 50, 0, 0, 1);
		myTranslatef(tgle[2] * 1.1f, tgle[3] * 1.1f, 0f);
		myScalef(0.041f, 0.041f, 1f);
		uploadMV();
		myRotatef(-((float) cnt) / 50, 0, 0, 1);
		myTranslatef(0.3f, 0f, 0f);
		uploadMV();
		char_func('J', cr);
		myPopMatrix();

		myPushMatrix();
		myRotatef((float) cnt / 50, 0, 0, 1);
		myTranslatef(tgle[4] * 1.1f, tgle[5] * 1.1f, 0f);
		myScalef(0.041f, 0.041f, 1f);
		uploadMV();
		myRotatef(-((float) cnt) / 50, 0, 0, 1);
		myTranslatef(0.3f, 0f, 0f);
		uploadMV();
		char_func('N', cr);
		myPopMatrix();

	}
	
	public void drawPoints() {
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer vBuf = Buffers.newDirectFloatBuffer(pIn);
		gl.glBufferData(GL_ARRAY_BUFFER, vBuf.limit() * Float.BYTES, vBuf, GL_STATIC_DRAW); 
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); 
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		vBuf = Buffers.newDirectFloatBuffer(clr);
		gl.glBufferData(GL_ARRAY_BUFFER, vBuf.limit() * Float.BYTES, vBuf, GL_STATIC_DRAW);
		gl.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0); 
		gl.glPointSize(2.0f);
		cr[0] = -1f;
		FloatBuffer cBuf = Buffers.newDirectFloatBuffer(cr);
		int posLoc = gl.glGetUniformLocation(vfPrograms, "uColor");
		gl.glProgramUniform3fv(vfPrograms, posLoc, 1, cBuf);
		cr[0] = 1f;
		gl.glDrawArrays(GL_POINTS, 0, z);

		for (int i = 0; i < z * 3; i += 3) {
			pIn[i] += dt[i];
			pIn[i + 1] += dt[i + 1];

			if (dst(0, 0, pIn[i], pIn[i + 1]) > Rad) {
				pIn[i] -= dt[i];
				pIn[i + 1] -= dt[i + 1];
				float dir[] = new float[3];
				float nml[] = new float[3];
				dir[0] = -dt[i];
				dir[1] = -dt[i + 1];
				dir[2] = 0;
				nml[0] = -pIn[i];
				nml[1] = -pIn[i + 1];
				nml[2] = 0;
				float dir1[] = new float[3];
				reflect(dir, nml, dir1);
				dt[i] = dir1[0];
				dt[i + 1] = dir1[1];
			}
		}
	}

	private void char_func(char c, float[] cColor) {
		int nolines = 3;
		float v[] = new float[nolines * 4];
		if (c == 'N') {
			v[0] = -0.9f;
			v[1] = -0.9f;
			v[2] = -0.9f;
			v[3] = 0.9f;
			v[4] = -0.9f;
			v[5] = 0.9f;
			v[6] = 0.9f;
			v[7] = -0.9f;
			v[8] = 0.9f;
			v[9] = -0.9f;
			v[10] = 0.9f;
			v[11] = 0.9f;
		} else if (c == 'J') {
			v[0] = -0.9f;
			v[1] = 0.9f;
			v[2] = 0.9f;
			v[3] = 0.9f;
			v[4] = 0.5f;
			v[5] = 0.9f;
			v[6] = 0.5f;
			v[7] = -0.9f;
			v[8] = 0.5f;
			v[9] = -0.9f;
			v[10] = -0.25f;
			v[11] = -0.25f;
		} else {
			v[0] = -0.9f;
			v[1] = 0.9f;
			v[2] = 0f;
			v[3] = 0f;
			v[4] = 0f;
			v[5] = 0f;
			v[6] = 0.9f;
			v[7] = -0.9f;
			v[8] = 0.9f;
			v[9] = 0.9f;
			v[10] = -0.9f;
			v[11] = -0.9f;
		}
		uploadColor(cr); 
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]); 
		FloatBuffer vBuf = Buffers.newDirectFloatBuffer(v);
		gl.glBufferData(GL_ARRAY_BUFFER, vBuf.limit() * Float.BYTES, vBuf, GL_STATIC_DRAW);
		gl.glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); 
		gl.glLineWidth(6.0f);
		gl.glDrawArrays(GL_LINES, 0, v.length / 2);
	}

	public void reflect(float vta1[], float n[], float vtx2[]) {
		nmlize(n);
		for (int i = 0; i < 3; i++) {
			vtx2[i] = 2 * dotprod(vta1, n) * n[i] - vta1[i];
		}
	}

	public float dotprod(float[] a, float[] b) {
		return (a[0] * b[0] + a[1] * b[1] + a[2] * b[2]);
	}

	public void nmlize(float vtr[]) {
		float dia = (float) Math.sqrt(vtr[0] * vtr[0] + vtr[1] * vtr[1] + vtr[2] * vtr[2]);
		if (dia == 0) {
			System.err.println("Vector length");
			return;
		}
		vtr[0] /= dia;
		vtr[1] /= dia;
		vtr[2] /= dia;
	}

	double dst(double a0, double b0, double a1, double b1) {
		return (Math.sqrt((a1 - a0) * (a1 - a0) + (b1 - b0) * (b1 - b0)));
	}

	public void init(GLAutoDrawable drawable) {
		String vShaderSource[], fShaderSource[];
		gl = (GL4) drawable.getGL();
		rdr = new TextRenderer(new Font("SansSerif", Font.BOLD, 20));
		System.out.println("\nInit:");
		System.out.println("\nLoading the shader programs");
		vShaderSource = readShaderSource("src/bounce_V.shader"); 
		fShaderSource = readShaderSource("src/bounce_F.shader"); 
		vfPrograms = initShaders(vShaderSource, fShaderSource);
		gl.glGenVertexArrays(vao.length, vao, 0);
		System.out.println("\nTo generate VAO to save the arrays of vertex attributes"); 
		gl.glBindVertexArray(vao[0]); 
		gl.glGenBuffers(vbo.length, vbo, 0);
		System.out.println("\nTo generate VBO (" + vbo.length + ") to keep vertex attributes"); 
		gl.glEnableVertexAttribArray(0); 
		gl.glEnableVertexAttribArray(1); 
		System.out.println("\nEnable the corresponding vertex attributes"); 
		System.out.println("\nFinally we load the attributes in display()"); 

	}

	public static void main(String[] args) {
		new njain25_CS551_Project();

	}

}
