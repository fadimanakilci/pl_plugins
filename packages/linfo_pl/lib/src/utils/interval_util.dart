/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © February 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import 'package:meta/meta.dart';

@sealed
class IntervalUtil {
  static const normalInterval = Duration(milliseconds: 200);
  static const uiInterval = Duration(milliseconds: 66, microseconds: 667);
  static const gameInterval = Duration(milliseconds: 20);
  static const fastestInterval = Duration.zero;
}