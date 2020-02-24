package vn.com.tpf.microservices.utils;

import java.util.Map;

public class PGPInfo {

	public static final String preshareKey = "123456";

//	key Prod
//	public static final String privateKey = "-----BEGIN PGP PRIVATE KEY BLOCK-----\n"
//			+ "Version: Keybase OpenPGP v1.0.0\n" + "Comment: https://keybase.io/crypto\n" + "\n"
//			+ "xcMGBFxqUAoBCADccPcfnbjzXYtSwzdgdkACeHWbP6/f9veBN3h9pGMbTgV7DOVz\n"
//			+ "2Gy8/MU4Ew4fV3k89Pf7JvQYmXpEWqz/0tATZnqJgyEv5LcqD+swuZTeA7CvTqQm\n"
//			+ "ndS+u+lDC+cGHyiAndwoirQfSdtExrQVHKmT9YOwCkj4DSWMnuwrWh13Hqx7NNBU\n"
//			+ "pS0jcKvG1d+wqsWVOrubryS8ccsAW+mNo46QWyJ7ZJseSn462veqi/N24kEIO/Uq\n"
//			+ "pD5rw+8dkvAxUgiZuIbMvF1mL19E6ZvCLkMrSJS9yVbfNp+yFlE+NcgoP17QPQdr\n"
//			+ "sAdpkusrLQoXO5UKYDAuQotf0m1SAQN0oXbPABEBAAH+CQMIU4ApBGqxwjBgdY2C\n"
//			+ "iG6xT2rLNeQwkWFYHxEGbhWbRwGpxtWRVrb0x4IebzDAAEyJl3QdLg/G0CVATzuq\n"
//			+ "5E6CFfHVv5mgFgNgXBsa9jdaBKFuI+hoBxfq2JmCW1p2jYEyYxhTBHQM/0TgJ09l\n"
//			+ "SX/1/sVuYZE86Iqh2DZX98czN9BdM2D0Shc7BBUoS90NsCgfg0BRdJfk2B0ELcYK\n"
//			+ "L2qSHN/Zui9Q+kpyIxmhsfQXTl4CFh7YvMH9jDXVuANK5iLqF+wDQbeTfZfoiHsy\n"
//			+ "7blR8xRehN5E3HGPj0ati5+67qFSBTZCyxDmA5wXvRdPJ4xf9yr+A0+Va/q/i4Vs\n"
//			+ "8xiPhmUKTLHOn24vsJG4uRxkxt7sVYSpj7F8dOJhK/cwGDoifsdpyXBuMHus6I6w\n"
//			+ "kFkAXzhsUYxNArg04em6eWPLXo316EHX5jIY7DvgsEjuQtTgO55s7D/JnDdnpGNd\n"
//			+ "jePA5mfb+wplZ8qAkZC4XK5es5jLDJTFhepyQgys925JLPWD6YmkpNYwbHZBNpOE\n"
//			+ "bjJRx39LDgID+KzBIdCeQwMPsoMfFk00GDUZlS+drc2mLfgrx2VFR3+IbKmhcram\n"
//			+ "kpfEFfCcSRmVIZEIMpjhjBRS/jriIdT8XC80iUTSbVp/TTuS7IEYcV6NVJ/VYdFA\n"
//			+ "f5ywzBeT4Hj8KAIqZqoy0FpF77Ofb/2B8IDrB8jCkGU7yT50r2OpIFO2ffgG8Rlr\n"
//			+ "yx9kTjmzlXnMgm5viytvtCym5fv+rXNO8nzpF1cMYdE1EHYN5qeTKVby5Hfb2pSO\n"
//			+ "ip3raIiP6JPYag4FeAHJboslpcACrJkUj0ndavgGJq63HR1214abRrjQXnd1ojAK\n"
//			+ "0QFfglqYnTV0LYm5EGpPc6DvF0UqXaeGOP3wXRX26SKvsEpFgzX36W0f9C2HOQEH\n"
//			+ "//BYvYFeK1GpzRpRdWFuZyA8cXVhbmdjdkB0cGIuY29tLnZuPsLAbQQTAQoAFwUC\n"
//			+ "XGpQCgIbLwMLCQcDFQoIAh4BAheAAAoJEGxNCMjf2BpqIXMIALZ7lwTbvAyFedP8\n"
//			+ "XQuP6riefYVSAi6k8fTH9GW52SThecj+B3WN8PpWMuD8dhrLuxxgXqYDKlB+e3SI\n"
//			+ "j3RKSKF4/gt9H4PNDwyMpFVOKDxtYZwI9nYWvl/9jDBFoimVn2U3OzM/HD+E9QeT\n"
//			+ "+sC9IxEnQ6FBhpHHFRmLWl+cmHqGH3g9PirS3uiMnyOMMbkCHSDT+74q4jkkIbKl\n"
//			+ "PEzMuQwLgweyYl3p2WrGWn850+yAwODImNEC7YYi63EyMmty4YXAmsh7FXzjMXB4\n"
//			+ "2VX11vPga7Jt5L5yoPciLaJR1f9PhXFvIk5WmYMaibJWVp3mkh7miCv4CyIJpAPY\n"
//			+ "ExEloX3HwwYEXGpQCgEIAOpx85QlGqhHZV2V01LiiquWChKKnsAKwklrgg73tPo7\n"
//			+ "lilsDP248SaPwnoey2d47Y0F5qjPMZn5bDhvAsZNxPGUyb7K9fcKFaW9ecQGoMOS\n"
//			+ "AgxH5NZKFgZdxfmLt5umAJDxwdhCSIiMB+QfSwCL7YEgHrevdklfIx880VsQjtP+\n"
//			+ "WKKPlGX67FemyB31N6N+Gpq5iBdaFHZMeKj8IXLLtVpLjW43jkxQmf+nOUOZ1giq\n"
//			+ "G21tR6fa/wf2bcE4ESlIYBpugAutVlrVCfg1PCcFPej85pyIMQHrJ5l+KfAa5Qx7\n"
//			+ "o665e7bjzzbQ6NpctiT8h8dUTOMiXRPekhZ6RTfEeVkAEQEAAf4JAwjgzEaLq2uF\n"
//			+ "02AlZHqsmD+xNa26Xgon1jdqULW5hOyauEb3vr+I9jmQZRUn9+ttaatVpYD+xXsd\n"
//			+ "kgW4Gy6BJ5EiFXCOpihnW56HevgveOjme2wLTVjJcDj+HyzNxrlEcda8/5CNyD9H\n"
//			+ "llZS8YM6xsER2jZNB3YO1uN4JiOiuBT9//XKl9b3VzkjJBwnsAcqT8FKusE9maiv\n"
//			+ "aIRKbmMvQUbmhXUQVXIPnrGkA5WWWmGpkQD24vhoKTOXjEsOPbHtFWL1yeedJ7e1\n"
//			+ "gsHKzBuNsi0xhtsKgKdegwrFnq6o2gc8DzMjGqS7zJp9MJMWj+jmI8ZRAaUEKuW1\n"
//			+ "u7hs6gIW/HrWZuN/cBhBDEY/i3VxAPVpepGQOmz7HSjlLZYYQFX1ewGBwwsZyQbh\n"
//			+ "+e9WSW2xaeuPhyeADJaGIc0sCKGTKeCFvVhcMgiJOEqoPXoZF3D6ARpT9D8tBBtp\n"
//			+ "94ry/HRJtkSmb2R37BnOrytSIN9PFFQfU0maPQB2xGndWEwQUQLCbOZ0mnDcVmS9\n"
//			+ "fAVg7E76uT5XWX2st1cWlbgknw9SVLBA3USum5pylfRHg1YG5kHRoMVrWlwfW6Xu\n"
//			+ "l26tNRNoTzaJ1IG2cKE2GNem0xDtK2Xg77VGpILJXJoMpBSMP1pxfZyu08xNP1ws\n"
//			+ "jNm09BPdqXzrzsO71Etmhvsm5Q95Xhtn/AKsOWwZpZ+B0Jn/OVBEgBu4LfSA9eQS\n"
//			+ "POw2y//5R45njxhODs7qmuA4i4WVSqJFzPh097RUumcHpq6X7sPpPo8H2jIzlLqO\n"
//			+ "YTg7igXR459bxttD3y8jNqDVJr/4gVgieB/PxOHSgIxrtNVoUAS4psKnBnSadZxe\n"
//			+ "lD1frGRIsJk2SLMkryNdFOB4wYJMzL3xNvtXXhWxh0urXZRM/rNN1/x4G2ZQVDo0\n"
//			+ "Hh6tptIqOx8wK5utY9LCwYQEGAEKAA8FAlxqUAoFCQ8JnAACGy4BKQkQbE0IyN/Y\n"
//			+ "GmrAXSAEGQEKAAYFAlxqUAoACgkQpkbCPhJtZRipJQf9H2BsEMEHXHErS6Xymfhm\n"
//			+ "L1NfpaDPyCQEO8NRk26FCXXoz6wogVMhg4yE5xTeK81lVsidwhIhhkHGzL1/aNab\n"
//			+ "SHoQCr7NoTXzaBkOULD6NqZAv4dTqX7+8tSBt/YXzl0uN5REiP/RnObQwIe1UhS0\n"
//			+ "XPtI19Lk1ykOC+HxGJ8b7cZG5XQlAnRMBd+qWOY08+EyQEsOd8g0gPB16/VFv+2s\n"
//			+ "dXukNqzGZKt6O2SzH9kKlzlZYMUeq/D2wpjDb+0ZUE6wAplILEEpt1ldk10y5KH0\n"
//			+ "vZk1SGo1nv4SnBwyHxbXJLtRc/EXcbn6nbJYk6Y2nS03YCZ0yFtIWxw+kKkOwx/2\n"
//			+ "ndmZB/47EpRPExDHyscq7voxSsyAI4vMksEjdBAmtXZkMQdM/4SmgMBpMwzcfNBy\n"
//			+ "/CiqVcKTwTW09ZzBNjQ5TJnR5l9ttqT/wwvYkTqUz+m9SqPwZImqB6aM8fAw6ks4\n"
//			+ "xrxQ29pKerot4G7JKQnIZiWH0k6T2zLMJYs2HeI2XZgFi+8ygNMJQJEwZ+AzjyCB\n"
//			+ "P+Z/4baZuD0sXvrXLrPQGHPOQtra68kCazRUrqv/JO5Vnqx/ub0Nv3zZPSHcUeGS\n"
//			+ "NkGGIJqXgc1KEHaVsjxnQWsvq1v/Glug8qNTK4KyJdqAD5BuHuhFUI1t33y5gp2E\n"
//			+ "I9U6ckaVfa5utXdQhRcJZAdeePkax8MGBFxqUAoBCACmhovs6ou/gq5O98iHWTuX\n"
//			+ "AKvRgvfU+REnHTHBHWRjFgsKyhpSrRADl+DBLrcx6J108ztPKdqYqarfvjgEfYjA\n"
//			+ "Xqbzp0tVf1xV0ByhKmGosaJhHwRunwReWhNq32xxdXytPDlyqt7RDsvTYtZ2TEyl\n"
//			+ "h0vD7ogMo/ijF6hfWFdt9ILtaNY1j3vdmXGg/6CcROb6iem6t3ecbp96q0liPYPm\n"
//			+ "sKsxzi7VYLmSCfFy2Cb0wCT0kxMfSN3iKEL1rh5MrlBz3/kJ5bmsa3EoMPt9XlQD\n"
//			+ "Tt7yB6ek8WWoXMBaW4tiPPcrayLH6ceMi5W9Oq61zpHfbr/UoJcNFat2dy2x4Cp1\n"
//			+ "ABEBAAH+CQMIh3nuLivDejBgMkIQA7EXn/ukZmp6O3QGy7ncHcYyB2MIS/7/oVOT\n"
//			+ "BsTJHNx+wGvRoN6L9sG4Q0n9V6up4SSoZSsSjU0XqwxnI7G/OOcQibbew3lcA/sU\n"
//			+ "NeCiJ0qB9tCcc8usmLAeMaxehy67AwWYzcIETshvb/Jt97RbvnC4S7SSjbU3o8TE\n"
//			+ "wGlqRJZN2RbeJC5uXzv1J7nixw7WZG7meKw7sAWEJ2vwgfz62cmw1DqY3M4k8tb7\n"
//			+ "wTjyN/nF3625dBQsjVf86Bd3GC8kpYZdLXnxqlXv/Z8OSsROJ77KZ0GvCJDOrT+s\n"
//			+ "zORQkqOXq5sOWz+E39eA7Wz2iTT2/LwBKYtHWwmNncJdmpkjmcg/avVVewipfvp2\n"
//			+ "/ZWEJQGEq4+LCgdSF9lgDot5zAxxSNtuK3ynCHNbMZEvRKv3mP+5glrnQ/S0e9yf\n"
//			+ "lCtNzPFMsc/7R1i0huqvNUB2UWRT/JVCM07rtqMPAC6qDEYePhD3gbZGtePTt96k\n"
//			+ "BQ5YxaS7Ca2ovkm4GqKKMHSUakfSA9S2omys18nBGDTwPAu0qGtotISOKNbFG24h\n"
//			+ "06DyevjACeqenvmd8+zUjZH/3mXGWRDVe24vr85IoIUTOjzfzOFgeCBpy1ILOoQT\n"
//			+ "ld8pB+DVq7vMr08cdv5QOypKYkO7fLfCf/uJiTOqa00zhLlbc9cifQNJ9wCjjElx\n"
//			+ "hpy6+y0o9ApckBJaKANVcV9t3yCtpVwcARw9DDpN5L6Qxpzx2jL4NYqHbWHR3R/K\n"
//			+ "Q4Q1z7/vDtjkfxFkalbNA2YNjP50fCfloF9fAmoMpS9WY1xToStS4FW+UuznQ0eY\n"
//			+ "PH1t/B/q95C5uKSd1BFAcss2B3ZQ0Ccw4kzKNVQDi3V5pEF3742It1w3Gajj5caw\n"
//			+ "Il7YKC82jCN8sscPb3om3fcyelbeYejM6uLlE9lnwsGEBBgBCgAPBQJcalAKBQkP\n"
//			+ "CZwAAhsuASkJEGxNCMjf2BpqwF0gBBkBCgAGBQJcalAKAAoJENPU88iSyofpNQYH\n"
//			+ "/2y1Oc9r0i2x22K+FLEaGIjkqJGjkCHaaStO4lkF3ikvgpzzPU47MahR3v51aALA\n"
//			+ "hxQBjLsvWd3jam2gMkZvi0XYImFOb0+T7K6dzZ+XKu8VntkPdvnyh2S2TmrbYMla\n"
//			+ "seIfF1LqyPH6xr5ujnKz9e6Cxg9dRipWqNQU0I5r/VQQO5TttO/Pb32yQy1or7kt\n"
//			+ "a2KFtcog73dIqVZWokP7ssTVD1BPZKkOyiy6iI93EAe8X+llbO+TaEauwy+Yf1F0\n"
//			+ "peMAXnPvrMpdNKv76F0pZhbPyT5HvfLTrvxYREOhAh/FDArICSsEcz2ejABKuY4H\n"
//			+ "Np1u/lqULR8joxGOTNKlnHT1Hgf9Ef4W9hAGTnhHNYj7+ZwWx5OJXR72ljRKfFCW\n"
//			+ "S4bP2c9gD7yAzZw1Ickgw+8g93uvXtgbC/04Fvdb7pLIinWzZD4L0v4nVBuMlrRB\n"
//			+ "E1rc8Wqwbnp/IHnWZBAMguMKCUKgpy3s3q4rEBebs0sNbfS0oqGiqPHLOAhUgBzf\n"
//			+ "15iR/+zL46joVlpoFFADsbBTjGa+hIWggpsSMofqTEGPgyTOKnGFjB8gYFr7Wf8f\n"
//			+ "lthDsOB+DM6rtfxzVQ4pY6DeT5Z5UyiCDrd/JhZkBLNYnH+0XJsVqahOEqp/IEMO\n"
//			+ "gsDvge2KkmgCnPCs4jrOzmG1zpsFpFFy9bb+6lPNcDqPG4QnwQ==\n" + "=V+wD\n" + "-----END PGP PRIVATE KEY BLOCK-----\n"
//			+ "";

	public static final String privateKey = "-----BEGIN PGP PRIVATE KEY BLOCK-----\n" +
			"Version: Keybase OpenPGP v1.0.0\n" +
			"Comment: https://keybase.io/crypto\n" +
			"\n" +
			"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\n" +
			"wxjYckDUBeFf7pvP9svWMWBSxseN\n" +
			"=T+BI\n" +
			"-----END PGP PRIVATE KEY BLOCK-----\n"
			+ "";

	//	key Prod
//	public static final String publicKey = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n" + "Version: Keybase OpenPGP v1.0.0\n"
//			+ "Comment: https://keybase.io/crypto\n" + "\n"
//			+ "xsBNBFxqUAoBCADccPcfnbjzXYtSwzdgdkACeHWbP6/f9veBN3h9pGMbTgV7DOVz\n"
//			+ "2Gy8/MU4Ew4fV3k89Pf7JvQYmXpEWqz/0tATZnqJgyEv5LcqD+swuZTeA7CvTqQm\n"
//			+ "ndS+u+lDC+cGHyiAndwoirQfSdtExrQVHKmT9YOwCkj4DSWMnuwrWh13Hqx7NNBU\n"
//			+ "pS0jcKvG1d+wqsWVOrubryS8ccsAW+mNo46QWyJ7ZJseSn462veqi/N24kEIO/Uq\n"
//			+ "pD5rw+8dkvAxUgiZuIbMvF1mL19E6ZvCLkMrSJS9yVbfNp+yFlE+NcgoP17QPQdr\n"
//			+ "sAdpkusrLQoXO5UKYDAuQotf0m1SAQN0oXbPABEBAAHNGlF1YW5nIDxxdWFuZ2N2\n"
//			+ "QHRwYi5jb20udm4+wsBtBBMBCgAXBQJcalAKAhsvAwsJBwMVCggCHgECF4AACgkQ\n"
//			+ "bE0IyN/YGmohcwgAtnuXBNu8DIV50/xdC4/quJ59hVICLqTx9Mf0ZbnZJOF5yP4H\n"
//			+ "dY3w+lYy4Px2Gsu7HGBepgMqUH57dIiPdEpIoXj+C30fg80PDIykVU4oPG1hnAj2\n"
//			+ "dha+X/2MMEWiKZWfZTc7Mz8cP4T1B5P6wL0jESdDoUGGkccVGYtaX5yYeoYfeD0+\n"
//			+ "KtLe6IyfI4wxuQIdINP7viriOSQhsqU8TMy5DAuDB7JiXenZasZafznT7IDA4MiY\n"
//			+ "0QLthiLrcTIya3LhhcCayHsVfOMxcHjZVfXW8+Brsm3kvnKg9yItolHV/0+FcW8i\n"
//			+ "TlaZgxqJslZWneaSHuaIK/gLIgmkA9gTESWhfc7ATQRcalAKAQgA6nHzlCUaqEdl\n"
//			+ "XZXTUuKKq5YKEoqewArCSWuCDve0+juWKWwM/bjxJo/Ceh7LZ3jtjQXmqM8xmfls\n"
//			+ "OG8Cxk3E8ZTJvsr19woVpb15xAagw5ICDEfk1koWBl3F+Yu3m6YAkPHB2EJIiIwH\n"
//			+ "5B9LAIvtgSAet692SV8jHzzRWxCO0/5Yoo+UZfrsV6bIHfU3o34amrmIF1oUdkx4\n"
//			+ "qPwhcsu1WkuNbjeOTFCZ/6c5Q5nWCKobbW1Hp9r/B/ZtwTgRKUhgGm6AC61WWtUJ\n"
//			+ "+DU8JwU96PzmnIgxAesnmX4p8BrlDHujrrl7tuPPNtDo2ly2JPyHx1RM4yJdE96S\n"
//			+ "FnpFN8R5WQARAQABwsGEBBgBCgAPBQJcalAKBQkPCZwAAhsuASkJEGxNCMjf2Bpq\n"
//			+ "wF0gBBkBCgAGBQJcalAKAAoJEKZGwj4SbWUYqSUH/R9gbBDBB1xxK0ul8pn4Zi9T\n"
//			+ "X6Wgz8gkBDvDUZNuhQl16M+sKIFTIYOMhOcU3ivNZVbIncISIYZBxsy9f2jWm0h6\n"
//			+ "EAq+zaE182gZDlCw+jamQL+HU6l+/vLUgbf2F85dLjeURIj/0Zzm0MCHtVIUtFz7\n"
//			+ "SNfS5NcpDgvh8RifG+3GRuV0JQJ0TAXfqljmNPPhMkBLDnfINIDwdev1Rb/trHV7\n"
//			+ "pDasxmSrejtksx/ZCpc5WWDFHqvw9sKYw2/tGVBOsAKZSCxBKbdZXZNdMuSh9L2Z\n"
//			+ "NUhqNZ7+EpwcMh8W1yS7UXPxF3G5+p2yWJOmNp0tN2AmdMhbSFscPpCpDsMf9p3Z\n"
//			+ "mQf+OxKUTxMQx8rHKu76MUrMgCOLzJLBI3QQJrV2ZDEHTP+EpoDAaTMM3HzQcvwo\n"
//			+ "qlXCk8E1tPWcwTY0OUyZ0eZfbbak/8ML2JE6lM/pvUqj8GSJqgemjPHwMOpLOMa8\n"
//			+ "UNvaSnq6LeBuySkJyGYlh9JOk9syzCWLNh3iNl2YBYvvMoDTCUCRMGfgM48ggT/m\n"
//			+ "f+G2mbg9LF761y6z0BhzzkLa2uvJAms0VK6r/yTuVZ6sf7m9Db982T0h3FHhkjZB\n"
//			+ "hiCal4HNShB2lbI8Z0FrL6tb/xpboPKjUyuCsiXagA+Qbh7oRVCNbd98uYKdhCPV\n"
//			+ "OnJGlX2ubrV3UIUXCWQHXnj5Gs7ATQRcalAKAQgApoaL7OqLv4KuTvfIh1k7lwCr\n"
//			+ "0YL31PkRJx0xwR1kYxYLCsoaUq0QA5fgwS63MeiddPM7TynamKmq3744BH2IwF6m\n"
//			+ "86dLVX9cVdAcoSphqLGiYR8Ebp8EXloTat9scXV8rTw5cqre0Q7L02LWdkxMpYdL\n"
//			+ "w+6IDKP4oxeoX1hXbfSC7WjWNY973ZlxoP+gnETm+onpurd3nG6feqtJYj2D5rCr\n"
//			+ "Mc4u1WC5kgnxctgm9MAk9JMTH0jd4ihC9a4eTK5Qc9/5CeW5rGtxKDD7fV5UA07e\n"
//			+ "8genpPFlqFzAWluLYjz3K2six+nHjIuVvTqutc6R326/1KCXDRWrdnctseAqdQAR\n"
//			+ "AQABwsGEBBgBCgAPBQJcalAKBQkPCZwAAhsuASkJEGxNCMjf2BpqwF0gBBkBCgAG\n"
//			+ "BQJcalAKAAoJENPU88iSyofpNQYH/2y1Oc9r0i2x22K+FLEaGIjkqJGjkCHaaStO\n"
//			+ "4lkF3ikvgpzzPU47MahR3v51aALAhxQBjLsvWd3jam2gMkZvi0XYImFOb0+T7K6d\n"
//			+ "zZ+XKu8VntkPdvnyh2S2TmrbYMlaseIfF1LqyPH6xr5ujnKz9e6Cxg9dRipWqNQU\n"
//			+ "0I5r/VQQO5TttO/Pb32yQy1or7kta2KFtcog73dIqVZWokP7ssTVD1BPZKkOyiy6\n"
//			+ "iI93EAe8X+llbO+TaEauwy+Yf1F0peMAXnPvrMpdNKv76F0pZhbPyT5HvfLTrvxY\n"
//			+ "REOhAh/FDArICSsEcz2ejABKuY4HNp1u/lqULR8joxGOTNKlnHT1Hgf9Ef4W9hAG\n"
//			+ "TnhHNYj7+ZwWx5OJXR72ljRKfFCWS4bP2c9gD7yAzZw1Ickgw+8g93uvXtgbC/04\n"
//			+ "Fvdb7pLIinWzZD4L0v4nVBuMlrRBE1rc8Wqwbnp/IHnWZBAMguMKCUKgpy3s3q4r\n"
//			+ "EBebs0sNbfS0oqGiqPHLOAhUgBzf15iR/+zL46joVlpoFFADsbBTjGa+hIWggpsS\n"
//			+ "MofqTEGPgyTOKnGFjB8gYFr7Wf8flthDsOB+DM6rtfxzVQ4pY6DeT5Z5UyiCDrd/\n"
//			+ "JhZkBLNYnH+0XJsVqahOEqp/IEMOgsDvge2KkmgCnPCs4jrOzmG1zpsFpFFy9bb+\n" + "6lPNcDqPG4QnwQ==\n" + "=kBUb\n"
//			+ "-----END PGP PUBLIC KEY BLOCK-----\n" + "";
	public static final String publicKey = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
			"Version: Keybase OpenPGP v1.0.0\n" +
			"Comment: https://keybase.io/crypto\n" +
			"\n" +
			"xo0EXd4WPAEEAN9fY/lHxLmLkg79z055H7PV0y7FubPINchFnFcqwTByG/4BSayb\n" +
			"DXFEE42FmqecGJXe33TUsXlY5SioY4wjotC8g/Wp5XzoNFOwDWPMMr/RjGvn8XE+\n" +
			"Q9rAOG15ER2uXDm3VNl+G0xKGLIE4bSuB1AUGdeTk1uZRFpWMZbe97AnABEBAAHN\n" +
			"HWFhYSA8bmd1eWVuLnBodW9uZ0BnbWFpbC5jb20+wq0EEwEKABcFAl3eFjwCGy8D\n" +
			"CwkHAxUKCAIeAQIXgAAKCRA0Vy5E18Yb+r1EA/96wiZA8oneJnj68yoPeylplslQ\n" +
			"xkUTjqwO3xlHLg5QSk5xGNaW2yyC8Zwov6DC1ew+kySWUBBVL7HtwHZiR74ShV0b\n" +
			"i5fTSYWXisE1z4puKI9wfF4uAnN320JTDg/ZdWacDzBlASyRmnJiej3+sGcatbM8\n" +
			"MavQTrJzjVGiDbHPNs6NBF3eFjwBBADKIg/KOuduzxGxIiYXE1OT0J8IKb3jvJ7G\n" +
			"6qKsKwX6gJcWTYvziQJdFgLVp3MHEwMMjdRaFhZFBSbfk6mHKljTd599qN39106r\n" +
			"WlJLxrE3iAbr75QNgBh2YyAQcJLUMxuIhOxKVvwVpkiQ8OZThlordNOzkPv7P/4g\n" +
			"Y6kj/48RBQARAQABwsCDBBgBCgAPBQJd3hY8BQkPCZwAAhsuAKgJEDRXLkTXxhv6\n" +
			"nSAEGQEKAAYFAl3eFjwACgkQ9/nZHotASLVl7gP/YbsDUGePrO+2+mncdn2nuj9f\n" +
			"M8sDdHzxmvIpMoPkT8NiAahXbVu4KgLDDtFCVAsT2H5TCNMaA0dQTM00kQfzvSV/\n" +
			"FCY5fR2Pys4rOszEicl0IDGpj9SxSylbkgv8O0h3luLzAjDvCV3WlOs0wn0seBcv\n" +
			"3cAYo/P+XQNxVdaIkhbbIwP+P2bp/PFkNFB69/K6271Q7ODKmeCj3c91I9XyJapW\n" +
			"Ic61a3dCFTR8zWuLrqNaDpc0bpAbI/XOpB+Yfui5feul79TaC2tg9mepD0+7fdHM\n" +
			"FL5IUrGNp9X5W86jpBLjsa3/PcJof8qLMs5zEXgvl/rOIYxlJTNtcJC5ADAJ/G8T\n" +
			"EELOjQRd3hY8AQQAtp1xDIPpbrNu9ttCXeW4FN+O3mOKQg8RRejXPISe3MLAc8Zo\n" +
			"9cux2SfagmbHN+8/6wDSExMd+3cbkvA+qESZiH19FxYUiA00QxiujfeHG79s7zcb\n" +
			"6j9kktYuaDtJqNTbdW2wOJiUQGrCc8lQRBWVHF3vZf7xVpW+WwQkWnBXTVUAEQEA\n" +
			"AcLAgwQYAQoADwUCXd4WPAUJDwmcAAIbLgCoCRA0Vy5E18Yb+p0gBBkBCgAGBQJd\n" +
			"3hY8AAoJELT8oBpTHaXnqDED/RiA0nNPLP0yw13UhJUSBAcy21awEntDHSU+NSxq\n" +
			"b6sqwq/Z/RGD5IFPqkIFjDcnazZ/dW13g27v0e3pmEh31cRdjjWbAMwgxHN4CJyR\n" +
			"3XdzK2lx4VROtyUpj7E4YFkRR/PyAifHenjRx4sIxEPO4p1h9O0Pa3/Le4bVIDkZ\n" +
			"sEg0pvwD/Rct1toA4chXbHdsO7Q5sI9rdIN+J9hWynEN2J1qmLGtjTBorm4AKcQq\n" +
			"WNxBpgbW9ErqCUcpu7fA11dZ1i1gn/tGEWOz+E+jX370V+krEsHwCetShY7BmIkH\n" +
			"27rs0/1l/AgGU1NrMuETu5+pwxjYckDUBeFf7pvP9svWMWBSxseN\n" +
			"=ai79\n" +
			"-----END PGP PUBLIC KEY BLOCK-----\n" + "";

	public static final Map<String, String> publicKey3P = Map.of("momo",
//			key Prod momo
//			"-----BEGIN PGP PUBLIC KEY BLOCK-----\n" + "Version: Keybase OpenPGP v1.0.0\n"
//					+ "Comment: https://keybase.io/crypto\n" + "\n"
//					+ "xsBNBFwTg20BCADmQa8nLBDKoZIc5Ih3mg9dzctwSmSjXi6KMj5vaSwzEO1lq8VX\n"
//					+ "sPhP8EJMMatNgTDQ3HV8/Hx2tfq1IuCiFfiT53Ru6+JuvkmaQl3LoqTZ4UETS+io\n"
//					+ "qTgO5frH6WGYxwhSmEdrPr1gSHCSt7VWcj/JavUxLxecKLFy6w6BtOVoRRZXNj/t\n"
//					+ "rAcPGwDJ0GEpn2wr46AmTRD/vxG0wJgd44WI5LPbABeizdhIqYgY/UKv7hkTFQQV\n"
//					+ "T594Fu+mB57OtVCyU53eJyrz0S+eQqWr39PaTt11Qm9fiVry376SDum5BZRBI4c/\n"
//					+ "o3VqAkWbyBob/6/kaXjgOrl2QmiIv+gFXHgTABEBAAHNIWRpZXBuZ3V5ZW4gPGRp\n"
//					+ "ZXAubnRAbXNlcnZpY2UuY29tPsLAbQQTAQoAFwUCXBODbQIbLwMLCQcDFQoIAh4B\n"
//					+ "AheAAAoJEGE1tPukqEFL+mUIAJU1Reo7U/xHqTILDz3HUv/btQW46ox//KCiqY8U\n"
//					+ "Ri+t7U+bukbuHrH5GC1vutCc33LMHthIPYNPUJEZzpLyMrEpUiSfJLxcuAqIp3c9\n"
//					+ "LBcYByW1pXDRjvYT0lnZUL8dYv4EXVbiHPNQiMf5HlPtkjHsfiMSgUHt4fHhPAdw\n"
//					+ "IxGmJHdc9VR5bCINtwpT/nXdd3QWfBC2GmzneCji2n//rUOVq2at48lER8jwPCsg\n"
//					+ "XTc6kHYxtpM5kVleegdXfLxvsqipYM1VsOP+TKH45oL1zZ41mWqRLtAFcT9N7u9C\n"
//					+ "t6/B15czWpz1QsFgcjLJu9KNyl2JLxmun+gVZDS9wjW4W4TOwE0EXBODbQEIAKxl\n"
//					+ "EN/RcRwOY38oGvUvnYh56/fp6blCWgr+C7RvxJj0N8dDrlCXZzSeKjfEAznRJjmy\n"
//					+ "1kk6xRpRYBNhS9JkMDZiWArtpEH+OxPV/TtHhJRixZbe0QpTOfH9BLC8IduMgFBX\n"
//					+ "BnSWvvFvY+ipk73IQKhVcsg6AAhI63S2J1yUZOKaEUrENTiKdMoqKuzalaeweVge\n"
//					+ "xruB/hnCweEomzWSy0Pot2+msVCxV0XcN7ZWyWGvvl9P1Wb9gwat4Z+alMc0SbYn\n"
//					+ "ptHN4j7k/IeI1IOz+i1LFPOvOzrSVlZtSaOvuSBsJVJfwrfxIHB4bG6vjuFists4\n"
//					+ "OQCltE/dm92lKogn9e0AEQEAAcLBhAQYAQoADwUCXBODbQUJDwmcAAIbLgEpCRBh\n"
//					+ "NbT7pKhBS8BdIAQZAQoABgUCXBODbQAKCRBEBUlUCJpxGw0HB/0d74y4i0WmEVum\n"
//					+ "nTXr7xrTDGT6suU18xTiPcn+8MY/Z2dOpwhHaEYhCbAXsPcarzvUfMO6ISbOdiEc\n"
//					+ "mLHs9nE4eg/5wcUhZW71DT+GMQSDz3nFyJ+Io3jEDyNc+Px5hhLrqDY7jeYq6mIr\n"
//					+ "lhdZ0gvd+RzFFXUqknBgMp8qxMDv41GmNxsjmOHng5whA5u5dyyvBHT6HgQrs0A9\n"
//					+ "llxwtci1vDUVpJ3xypGp1E4y33gKvDPXnYc2rBvHCT6z0utnTfxY0b3RCcJroSXs\n"
//					+ "kcwmw9kpKctoSzKoEmQMA6VcBLBeR1OlzUMo7e2m4PTg9r0VktXVuHRmdfvsimRq\n"
//					+ "aPt6bJSl530IAOLnKfXu55iyOuZB7Nb1s31rGM0ulr48iHCaepRHeur79pFQ339z\n"
//					+ "FBRFiaWlDA6x1VU5mZXmRNDufCwynEQ/pDGbCGVZiRjH8KljepE+RV7H1UT4Z46b\n"
//					+ "KnVntJmj/kxMT7U+iB7iE0mQRW65jaQGGNXJlU8pw60Xnw0pObdcu7cTzbI4dHeS\n"
//					+ "F8LNlZ9oH6t3xjsQPMFPCbBGOe9vbeeP5ZR6KQxD8ylghaJRW6vhj2y0KsDPQmUH\n"
//					+ "ZLaMTqtw8EmIxgBlQPsOjzxz7uokzGXMRhJqO2STw+rc47HgxjzdlGlBdpKHspdL\n"
//					+ "BOFNFRWw4cUQEvB+yzEDNgo+IVl2iKnW7HPOwE0EXBODbQEIANfoiVzohX0cJpMg\n"
//					+ "fcL4BCxTylaOw3B5d9PE5WCixRnuQPtS34EvTCV/yD3NUk82BFvIur7hFInn4G8C\n"
//					+ "sggnO0RKCIcMBYiyEcf0c5uTHFOdOp1t82EwAcHrDDbw+z46cDBvCRGg33Wdv743\n"
//					+ "eX8V/oi1Bcpe1cXLJmLDMmGBOTv2QxymuD8jXKyv7VqkVdk/o+QUhDiOi5ve//H3\n"
//					+ "InOjlkK0rXvWATg/eggDy5vs0XD4EF5fqF11f4uZdKX38NWSURTxRaACpHFFdO2D\n"
//					+ "MJVvmgddiDEmRzHGiAFfqHJBUpw8phk5jgHmWRLIUgG6W+6fkxZ194zAdsBv+dJv\n"
//					+ "1UUxfb0AEQEAAcLBhAQYAQoADwUCXBODbQUJDwmcAAIbLgEpCRBhNbT7pKhBS8Bd\n"
//					+ "IAQZAQoABgUCXBODbQAKCRCV8USsnqOPc1ArB/4rqSISJpJ8xejt7EaihS97aEzw\n"
//					+ "ElS87/cLSQlbEJdgWGXLAaf6qUaVW5uAzd99SuVoIv+n5JAkyh7w7at+8DNCPThQ\n"
//					+ "wHq45PrOotJHGElR3Ffdk4AzyhBjFaPnIkr9N/sRQtq3WNZJgn8idwoajBr0nqtV\n"
//					+ "64xPUXRm37mQHonM4RazYLugkOKYHE5NjoxXRUZec8PN6gQ2e3plVwv/OswyOBDf\n"
//					+ "d0obctLeLX70ofaj9DbMoIflP96R0nK0hIAhW3IemZE+r2npgt5nqUC0OoGMsmzW\n"
//					+ "hbaXQlGOXpUZcCY9X2qoHkEDMnW+/jOQwGsuZ97TN7ywxqLqLkXZudZlZtPjNWUH\n"
//					+ "+gPGEgsPxE5flxKp/+UFq91s7C40YOYvIQ88aj0MlgbGdwzuDG1hIIHQBUjyMe1D\n"
//					+ "ZMbFkJ5GHZQUngszX/jQWpljFUHLf2jdKOu50Pq2RVOPy88eqFv4PiP/hL4HTfQq\n"
//					+ "FFMkyoGjvtOSnOj7LADMRIl5K1tYG9pQZV8PEE+81Yntoh/QTkv0LIYZesjr3131\n"
//					+ "bRMxtVD1d/oTZybL5Dy0CaGM9dAC34elDwtEVkVy0HqEXGstKD4+zpY72gqZP8IB\n"
//					+ "gC68sROaA2zR2tPtHGd8ANTMRVmBp0tMaMnDHdJ0HIiN2bw6GpA6ytTaWWLJNNpQ\n" + "CyDrKmFnscz5mqEx/zYpCUo=\n"
//					+ "=/jPH\n" + "-----END PGP PUBLIC KEY BLOCK-----\n" + "",
			"-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
					"Version: Keybase OpenPGP v1.0.0\n" +
					"Comment: https://keybase.io/crypto\n" +
					"\n" +
					"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\n" +
					"lUEEWQyT7ZCp5tkQUKXmo2q2RBM=\n" +
					"=cL9H\n" +
					"-----END PGP PUBLIC KEY BLOCK-----\n" + "",
			"fpt",
			"-----BEGIN PGP PUBLIC KEY BLOCK-----\n" + "Version: Keybase OpenPGP v1.0.0\n"
					+ "Comment: https://keybase.io/crypto\n" + "\n"
					+ "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\n" + "=o+JZ\n"
					+ "-----END PGP PUBLIC KEY BLOCK-----\n" + "",
			"digitex",
			"-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
					"Version: BCPG C# v1.6.1.0\n" +
					"\n" +
					"mQENBF2q6I4BCACFJoXr5uODFHUWetBv/wzk477zGRmpO3LD0DxZqDfe6QIVpg3H\n" +
					"XGeVZRD9DPPoYUuT9+ns2RFNoe9WjL7Hod6Yzf81Y++HgORu6kfr0Co7kkZlXTTa\n" +
					"m/9kxO1gQRW3UsjR4reBRqg8hT4dBID7nclQSQnkXNDny3ZiubuMqzdnUg8+MHNc\n" +
					"bfXB1TXOxX7iIQLtv7qPMfhi6HRC6sTXzHOFKt0NLjGfIl8cqWQbr1ATHvBnj/gK\n" +
					"uCB34HuMs7FYqU8oAyNqkZ6ObEH1rMBHN8fW1dNUWVfTGTt/cfw/1GryGexFv0Dn\n" +
					"CDlC7E/hZDUaQGdVNrS6O7rfd2GULvKMJSb9ABEBAAG0AIkBHAQQAQIABgUCXaro\n" +
					"jgAKCRBw0WNu5f9T9lTXB/9imQCxBgA4D0i6IUQm9UE55S4jsEma/TyPQiKb0bwy\n" +
					"Rc508XqB4ec7QZ5X9KxLLeNT4sb6jiECZGrzQuq9aKDjsMoVI8evJXrN6qE16G9s\n" +
					"Em/TwhN+UTVlOcq67O4g3zDRMYEE1/Ho+84et8w6R9XMrrNjUhWL2X8o14nJlb7u\n" +
					"cgsB/6Ts+asPfnY/O2xiNw1XT+v4F7w0fMYkp+IMtJHOmzaCiic5MNqCuL6JotfN\n" +
					"H6yN35iJRVFhXrqm1C8CwKNN/A54Jx+3LZX8SuQWJ6BJEVEALv5H9LEoGkXNgq+7\n" +
					"nMc+dM2RR/1xoNqtcivDBRw6p7350MJSXPlVKe/yXnTF\n" +
					"=lBb4\n" +
					"Version: BCPG C# v1.8.5.0\n" +"",
			"sgbpo",
			"-----BEGIN PGP PUBLIC KEY BLOCK-----\n" +
					"Version: BCPG v1.64\n" +
					"\n" +
					"mQENBF5PXsQDCACXg6XxFHeog0SVHAkWOt2uq8TOAubRLkcG18KYr2uxWSC+IPD3\n" +
					"OJ2ZmFVAMqOUst3bRo3YcN1v6A1MI/s/QB0fkg98+5NoiG3tYfLDGQvKIo+leut1\n" +
					"/O86sONpLXmL3nVtbKNhxmFLDO5GjkVfndW4CtvyrZo2RPjd0efMcmdE1GB1XcSV\n" +
					"4HNLldDi4XWNnvkJTvTHK0QsTJ/2stX3gKZxKbPScvaNUsRiTsnXXFCjHW0ee/b0\n" +
					"W9Kv0uzvBfY2jXCnCQb78X2O0F2OSRLaT2cHgNfi9K4bM8L/D8vf4RKzQIIiFlZr\n" +
					"ccsXU8FnzU2HvLH6ucCkq/Cb5tbEpWlPuKonABEBAAG0H25vcmVwbHkgPG5vcmVw\n" +
					"bHlAc2FpZ29uYnBvLmNvbT6JASgEEwMCABIFAl5PXsUCGwMCCwkCFQoCHgEACgkQ\n" +
					"0QnHNfbbp5lW8Af/TK3A7bWiBqXUzyfiOp3juQ6SRQUmeeES52LtbbKk6ywJtotu\n" +
					"fLfgHIEBCXrwjY0/rmmhz7uZPmccn3ZRRhEnYRLQ8rPqIn+1x20nOb5dzhpu+KZ2\n" +
					"vQ6KTylMWVcsONr6u8sjVF0rkwi1QPupwW44Kzf0mWp82HIeKYgc9B6zgf8GwIWw\n" +
					"p//c8s8RGgmJO3mdQCPLv1J2FBR4UBg3DA7oGrSwGbQ29YGcWt7YN5PKJ4VAoajM\n" +
					"NrkfHMctYhyHigM1fQqgwqcPWDReMbflfhzC0fhbKFVP298hLRPAk8UznB9WaX6h\n" +
					"KWVV/8mPD5cs12Kcl8BJef8FpE8F/Vwac1WYOLkBDQReT17EAQgA0ApDGEeuaXOv\n" +
					"uES1+9JnPg2x9U2BQJNWJeKO/S4z3sZV6QCKyldKWVM6WVOMaJXtEjNnzCH/cKIt\n" +
					"yePwKSg0aueh8oiNCDKW8bbLGREgxBIdkNs0sloOBBF0zC/29X8JbuofSC089Z9d\n" +
					"A0vFdMWhr16gaqrk1igBpWR9X/HgI+FmmFzunoEWQmzYOCTrQcP8HqXa7+izSxjg\n" +
					"H1y/QnAZ7pLSowxZepoNwDfHssz2eM6/s1mGpKDJi51KLHabRUcb9APCTq6yJk5f\n" +
					"nWPy1g23j1xNc46imlXaldJqZHxZGz0F0CfMEwFvxcM7RpaQegKZQHfg5sUSNlO9\n" +
					"Yk7gR8uIdwARAQABiQEfBBgDAgAJBQJeT17FAhsMAAoJENEJxzX226eZWWwH/A5j\n" +
					"S/BqebamWy3gvj8HJEtGCvvCdmX3Bry+3NURMbrsyBStRorVrNOYMZFDCb+S9a6g\n" +
					"lGwG8aA/jasWNFkfWw45ZRLBblZGakCHWNDedpjsYZ/F4yNy7S8mOdmlzhgzsmhi\n" +
					"EVh08p3tPkNKcuFD+kuduzHtBUWpzYo98CpygAgQhSHFhOHHYtLZJI5J+bquAZXA\n" +
					"bZ2T7grqXAW4M2EvpsKxjOC0FiyXzsM6SiRh5oLTzKb/50hkDZgnqG7xqzVHE3Jv\n" +
					"ITEX5Rv2fWR3233Fcnn5cO6U66Afg6/LV8vktKPkQeUu0YCqXPO5yVj+3n3Dj+KD\n" +
					"BEtu0Z7hQgBMRnBTpKY=\n" +
					"=tluh\n" +
					"-----END PGP PUBLIC KEY BLOCK-----\n" +"",
			"test",
			"-----BEGIN PGP PUBLIC KEY BLOCK-----\r\n" + "Version: Keybase OpenPGP v1.0.0\r\n"
					+ "Comment: https://keybase.io/crypto\r\n" + "\r\n"
					+ "xo0EXagaEgEEALUdJyr9b/iFYmjPR7by8ERoHsf4Gs3EB7GjJHVCSTpTHTWUOKIw\r\n"
					+ "PkdacOY5hidEZrgg4nH/U29ojStH+oxCjmMPQobGE1YVkVqoNdH3VO8LYD0zIxwZ\r\n"
					+ "ibNiy33aIIuzm5eTjlAa+9Gm+fKJYDjtlKPDlnv2z7MEYhih+BNjZxbtABEBAAHN\r\n"
					+ "GXRwZiAodHBmKSA8dHBmQGdtYWlsLmNvbT7CrQQTAQoAFwUCXagaEgIbLwMLCQcD\r\n"
					+ "FQoIAh4BAheAAAoJEOIlhMcKDbKv+tcD/A7jKYfxLGnd6c0W/BnC98Dxe0st7i08\r\n"
					+ "ta3L3P8feFpZb7twHVqjRB0NS7/y9hycMc00FnKtJQRnyidwJzWMSF9HNm18AMLk\r\n"
					+ "9vKoCLv0P3qT+3rQyEpNtfr9ICNiKPJSDmr33zsexKzqlpM6GwWrPhJ+Sd2wsQM6\r\n"
					+ "3erLXvBU4LyHzo0EXagaEgEEAOI4z0Uvxf7fXc/Pw+OxSGg1P7ZS9EEopQdhOgOi\r\n"
					+ "DgJDU9fRU1S18TD4SgS177+58L6eC6kvlBoR31t4s+8iefzLaOIy6i76nLM6/pr9\r\n"
					+ "umcrdyIeVn0FGKQjOrskaDeoLz2LqeDY7oLUgYe1cV21l6VsVi2IeRSMpJF2eaPJ\r\n"
					+ "pSuLABEBAAHCwIMEGAEKAA8FAl2oGhIFCQ8JnAACGy4AqAkQ4iWExwoNsq+dIAQZ\r\n"
					+ "AQoABgUCXagaEgAKCRBwEIQIPNfxbB38BADTviF8StucVQDfmOY4FraHkmNzJ66R\r\n"
					+ "l06Cwc3uxHQBhti4XQKJKrLlSd0mZRC6nHEjIRkefDFC1m5ty0vUPTutesm8OVY4\r\n"
					+ "7RG4fiy2OPWoRFGd/IKXUWGhY48U3Uqq+sKcwzYlxjceBjgKu8Bh6m4MPxcZa+7K\r\n"
					+ "MPUXhriIlBQDIBt1A/0XBXCFOjwSQIu441S5yDx8EXgaOX106XWCy2oEj2PAwUTQ\r\n"
					+ "OhKSePq5lROpq8SIWd4VZXZDCN/uKODtGW4/u5oxmeFtF9bsNpvEVUt6ynZmb34i\r\n"
					+ "kF9yu9r2GZKS2BSUYCbMEAsBIhZwPis2Caw5XzSHb2AtsTJZ9oEt9W2i/0ZFC86N\r\n"
					+ "BF2oGhIBBAD3nAqrmMii4DShW3ugE/0KOLdB0kCKcAOjhfz7Lts5y5XubyMRCZiQ\r\n"
					+ "AUrbc2Ypuc0m3iJId8ungNQzif7eqBcuP2khn3cGnKeIAlPOEi27h925XOygbUCM\r\n"
					+ "GeRakAQqt5AicADdBWttnx6y8CjqceIHF2gMzttHcrfbrvoB+yR++QARAQABwsCD\r\n"
					+ "BBgBCgAPBQJdqBoSBQkPCZwAAhsuAKgJEOIlhMcKDbKvnSAEGQEKAAYFAl2oGhIA\r\n"
					+ "CgkQrfydhLdvo0G8JwQAqqK+j1g4sw4vxaQjM5uJvEGvh3FZjKM6C4AYHYhURbx9\r\n"
					+ "51Wm/vIGbQ6bzUUSBBLAXVQwn0G8f75fCIOL9KuD2lok21ONvSt9VrslobtH9sPX\r\n"
					+ "scBI3S2rvpYsAAJLni8ADSGiJzbRluNSrDy1yrNTNYhxzUKlqohn+SocPdaHKzdZ\r\n"
					+ "0wP+MzCLdr18418OpiaVzQWu3cazuTr4xJ79jgyWCULpY1q1mzpYq0hQoGhnGlJ9\r\n"
					+ "kB/UE3tMZIQo6HiAAUx46DTnWWaHhhGh/ly2NQQwSHU56U8ha8S8rpg6E0D3Yw+x\r\n"
					+ "q3N/VspqfjIjyhoBC4z+a/PQG0qIn9ARRiiD8OJIlWJxw8c=\r\n" + "=0COC\r\n"
					+ "-----END PGP PUBLIC KEY BLOCK-----\r\n" + "");

}