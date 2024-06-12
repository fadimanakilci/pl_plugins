/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Copyright © April 2024 Fadimana Kilci - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Created by Fadimana Kilci  <fadimekilci07@gmail.com>, February 2024
 */

import '../domain/sources/orientation_state_protocol.dart';

OrientationStateProtocol parseOrientationStateProtocols(int? state) {
  switch (state) {
    case 0:
      return OrientationStateProtocol.undefined;
    case 1:
      return OrientationStateProtocol.left;
    case 2:
      return OrientationStateProtocol.up;
    case 3:
      return OrientationStateProtocol.right;
    case 4:
      return OrientationStateProtocol.down;
    default:
      throw ArgumentError('$state is not a valid orientation state protocol.');
  }
}