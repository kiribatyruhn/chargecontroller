/*
 * #%L
 * Organisation: mattjlewis
 * Project:      Device I/O Zero - Sample applications
 * Filename:     PCF8591App.java  
 * 
 * This file is part of the diozero project. More information about this project
 * can be found at http://www.diozero.com/
 * %%
 * Copyright (C) 2016 - 2017 mattjlewis
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


import org.pmw.tinylog.Logger;

import com.diozero.devices.PCF8591;
import com.diozero.util.SleepUtil;

/**
 * PCF8591 sample application.
 * To run:
 * <ul>
 * <li>sysfs:<br>
 *  {@code java -cp tinylog-1.2.jar:diozero-core-$DIOZERO_VERSION.jar com.diozero.sampleapps.PCF8591App 3}</li>
 * <li>JDK Device I/O 1.0:<br>
 *  {@code sudo java -cp tinylog-1.2.jar:diozero-core-$DIOZERO_VERSION.jar:diozero-provider-jdkdio10-$DIOZERO_VERSION.jar:dio-1.0.1-dev-linux-armv6hf.jar -Djava.library.path=. com.diozero.sampleapps.PCF8591App 3}</li>
 * <li>JDK Device I/O 1.1:<br>
 *  {@code sudo java -cp tinylog-1.2.jar:diozero-core-$DIOZERO_VERSION.jar:diozero-provider-jdkdio11-$DIOZERO_VERSION.jar:dio-1.1-dev-linux-armv6hf.jar -Djava.library.path=. com.diozero.sampleapps.PCF8591App 3}</li>
 * <li>Pi4j:<br>
 *  {@code sudo java -cp tinylog-1.2.jar:diozero-core-$DIOZERO_VERSION.jar:diozero-provider-pi4j-$DIOZERO_VERSION.jar:pi4j-core-1.1-SNAPSHOT.jar com.diozero.sampleapps.PCF8591App 3}</li>
 * <li>wiringPi:<br>
 *  {@code sudo java -cp tinylog-1.2.jar:diozero-core-$DIOZERO_VERSION.jar:diozero-provider-wiringpi-$DIOZERO_VERSION.jar:pi4j-core-1.1-SNAPSHOT.jar com.diozero.sampleapps.PCF8591App 3}</li>
 * <li>pigpgioJ:<br>
 *  {@code sudo java -cp tinylog-1.2.jar:diozero-core-$DIOZERO_VERSION.jar:diozero-provider-pigpio-$DIOZERO_VERSION.jar:pigpioj-java-1.0.1.jar com.diozero.sampleapps.PCF8591App 3}</li>
 * </ul>
 */
public class PCF8591App {
	public static void main(String[] args) {
		if (args.length < 1) {
			Logger.error("Usage: {} <adc_pin>", PCF8591App.class.getName());
			System.exit(2);
		}
		int adc_pin = Integer.parseInt(args[0]);
		test(adc_pin);
	}
	
	private static void test(int adcPin) {
		try (PCF8591 adc = new PCF8591()) {
			adc.setOutputEnabledFlag(true);
			float[] v = new float[4];
			boolean high = true;
			while (true) {
				if (high) {
					adc.setValue(0, 1);
					high = false;
				} else {
					adc.setValue(0, 0);
					high = true;
				}
				for (int i=0; i<v.length; i++) {
					v[i] = adc.getValue(i);
				}
				Logger.info(String.format("Pin 0: %.2f; Pin 1: %.2f; Pin 2: %.2f; Pin 3: %.2f",
						Float.valueOf(v[0]), Float.valueOf(v[1]), Float.valueOf(v[2]), Float.valueOf(v[3])));
				
				SleepUtil.sleepSeconds(1);
			}
		}
	}
}
