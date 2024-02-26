package student.util;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import student.HandleFileI;

public class ImageTransfer {

	private static final int IMG_WIDTH = 120;//71 * 3 / 2;
	private static final int IMG_HEIGHT = 160;//99 * 3 / 2;

	public static void main(String[] args) throws IOException {
		// exportXXX();
		exportSize();
	}

	static void exportXXX() throws IOException {
		File dirs = new File("D:\\aaaa\\weixin\\WeChat Files\\wxid_0lg821m9xx1w22\\FileStorage\\File\\2023-11\\23计应照片2");
		List<String> names = getNames(null);
		dirs.mkdirs();
		while (!names.isEmpty()) {

			HandleFileI handler = new HandleFileI() {

				@Override
				public void handleFile(File file) throws Exception {
					if (names.isEmpty()) {
						return;
					}
					String remove = names.remove(0);
					BufferedImage originalImage = ImageIO.read(file);
					int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

					// BufferedImage resizeImageJpg = resizeImage(originalImage, type);
					// ImageIO.write(resizeImageJpg, "jpg", new File("E:\\ target.jpg"));

					BufferedImage resizeImagePng = writeImage(originalImage, type, remove);
					ImageIO.write(resizeImagePng, "png",
							new File(dirs.getCanonicalPath() + File.separator + remove + ".png"));
					// BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
					// ImageIO.write(resizeImageHintJpg, "jpg", new File("E:\\target.jpg"));

					// BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type);
					// ImageIO.write(resizeImageHintPng, "png", new File("E:\\target.jpg"));

				}

				@Override
				public void handleDir(File directory) throws Exception {
					// TODO Auto-generated method stub

				}

			};
			HandleFileI.Tools.scanDirRecursion(new File("D:\\aaaa\\weixin\\WeChat Files\\wxid_0lg821m9xx1w22\\FileStorage\\File\\2023-11\\23计应照片"), handler);
		}
	}

	static List<String> getNames(String filePath) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new FileReader(new File(filePath != null ? filePath : "./学生姓名.txt")));
		ArrayList<String> arrayList = new ArrayList<>();// 存所有学生的链表（可以认为是比较好用的数组）
		String line;
		while ((line = bufferedReader.readLine()) != null) {// 是否文件读完�?
			String 学生姓名 = line.trim();
			if (!学生姓名.equals("")) {// 判断学生姓名有效
				arrayList.add(学生姓名);
				// System.err.println(学生姓名);
			}
		}
		bufferedReader.close();
		return arrayList;

	}

	private static BufferedImage writeImage(BufferedImage originalImage, int type, String str) {
		int height = originalImage.getHeight();
		int width = originalImage.getWidth();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.setFont(new Font("宋体", Font.PLAIN, 36));
		g.drawString(str, 50, 150);
		g.dispose();
		return resizedImage;
	}

	static void exportSize() {
		File dirs = new File("D:\\aaaa\\weixin\\WeChat Files\\wxid_0lg821m9xx1w22\\FileStorage\\File\\2023-11\\23计应照片2");
		dirs.mkdirs();
		HandleFileI handler = new HandleFileI() {

			@Override
			public void handleFile(File file) throws Exception {
				BufferedImage originalImage = ImageIO.read(file);
				int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

				// BufferedImage resizeImageJpg = resizeImage(originalImage, type);
				// ImageIO.write(resizeImageJpg, "jpg", new File("E:\\ target.jpg"));

				BufferedImage resizeImagePng = resizeImage(originalImage, type);
				ImageIO.write(resizeImagePng, "png",
						new File(dirs.getCanonicalPath() + File.separator + file.getName()));

				// BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
				// ImageIO.write(resizeImageHintJpg, "jpg", new File("E:\\target.jpg"));

				// BufferedImage resizeImageHintPng = resizeImageWithHint(originalImage, type);
				// ImageIO.write(resizeImageHintPng, "png", new File("E:\\target.jpg"));

			}

			@Override
			public void handleDir(File directory) throws Exception {
				// TODO Auto-generated method stub

			}

		};
		HandleFileI.Tools.scanDirRecursion(new File("D:\\aaaa\\weixin\\WeChat Files\\wxid_0lg821m9xx1w22\\FileStorage\\File\\2023-11\\23计应照片"), handler);
	}

	static void extractName() {
		File dirs = new File("D:\\Student\\20计应2\\20计应2\\照片3");
		dirs.mkdirs();
		HandleFileI handler = new HandleFileI() {

			@Override
			public void handleFile(File file) throws Exception {

				BufferedImage originalImage = ImageIO.read(file);
				ImageIO.write(originalImage, "jpg", new File(dirs.getCanonicalPath() + File.separator
						+ file.getName().substring(0, file.getName().indexOf('_')) + ".jpg"));
			}

			@Override
			public void handleDir(File directory) throws Exception {
				// TODO Auto-generated method stub

			}

		};
		HandleFileI.Tools.scanDirRecursion(new File("D:\\Student\\20计应2\\bak\\电子照片20级\\20计应2"), handler);
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
}
