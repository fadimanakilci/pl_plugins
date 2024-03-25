/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'dart:math' as math show sqrt;

class DisplayInfoModel {
  final double widthPx;
  final double heightPx;
  final double xDpi;
  final double yDpi;

  DisplayInfoModel ({
    required this.widthPx,
    required this.heightPx,
    required this.xDpi,
    required this.yDpi,
  });

  /// The exact physical display width in inches.
  double get widthInches => widthPx / xDpi;

  /// The exact physical display height in inches.
  double get heightInches => heightPx / yDpi;

  /// The exact physical size in inches measured diagonally across the display.
  double sizeInches () {
    final width = widthInches;
    final height = heightInches;
    return math.sqrt((width * width) + (height * height));
  }

  Map<String, dynamic> toJson() => {
    'widthPx': widthPx,
    'heightPx': heightPx,
    'xDpi': xDpi,
    'yDpi': yDpi,
  };

  /// Return as `Map`
  Map<String, dynamic> toMap() {
    return {
      "widthPx": widthPx,
      "heightPx": heightPx,
      "xDpi": xDpi,
      "yDpi": yDpi,
    };
  }

  static DisplayInfoModel fromMap(Map info) {
    return DisplayInfoModel(
      widthPx: info['widthPx'],
      heightPx: info['heightPx'],
      xDpi: info['xDpi'],
      yDpi: info['yDpi'],
    );
  }

  @override
  String toString() {
    return '\n    [WIDTH_PX]: $widthPx\n'
        '    [HEIGHT_PX]: $heightPx\n'
        '    [X_DPI]: $xDpi\n'
        '    [Y_DPI]: $yDpi\n'
        '    [SIZE_INC]: ${sizeInches()}\n';
  }
}