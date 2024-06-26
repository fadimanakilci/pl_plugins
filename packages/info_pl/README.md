# info_pl

[![melos](https://img.shields.io/badge/maintained%20with-melos-f700ff.svg?style=flat-square)](https://github.com/invertase/melos)

A Flutter plugin that gets an android device's information.

## Platform Support

| Android |
|:-------:|
|    ✅    |

## Usage

To use this plugin, add `info_pl` as a [dependency in your pubspec.yaml file](https://flutter.dev/platform-plugins/).

### Example

Import `package:info_pl/info_pl.dart` and to get platform-specific device information initialize `InfoPl`.

```dart
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:info_pl/info_pl.dart';

static final InfoPl _infoPl = InfoPl();

void main() => runApp(
      const MaterialApp(
        home: Material(
          child: Center(
            child: ElevatedButton(
              onPressed: _getDeviceInfo,
              child: Text('Get Device Info'),
            ),
          ),
        ),
      ),
    );

Future<void> _getDeviceInfo() async {
  if (!await _infoPl.init()) {
    throw Exception('Could not to get device info');
  }
}
```

### Info Text Generator Example

This plugin offers a data method that provides platform-specific device information in a generic manner.
Data obtained through this method can be visualized in an organized and readable manner.

```dart
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:info_pl/info_pl.dart';

static final InfoPl _infoPl = InfoPl();
DeviceInfo? _deviceInfo;
String? _deviceInfoText;

void main() {
  _getDeviceInfo();
  runApp(
    const MaterialApp(
      home: Material(
        child: SingleChildScrollView(
          child: Text(
            _deviceInfoText ?? 'Loading...',
          ),
        ),
      ),
    ),
  );
}

Future<void> _getDeviceInfo() async {
  String? deviceInfoText;
  
  try {
    _deviceInfo = await _infoPl.init();
    deviceInfoText = _deviceInfo.toString();
  } on PlatformException {
    deviceInfoText = 'Failed to get device info.';
  }

  setState(() {
    _deviceInfoText = deviceInfoText!;
  });
}
```

<p align="center">
   <a><img width="30%" src="https://raw.githubusercontent.com/fadimanakilci/pl_plugins/main/packages/info_pl/assets/screenshots/Screenshot_20240325_233710.png"/></a>
   <a><img width="30%" src="https://raw.githubusercontent.com/fadimanakilci/pl_plugins/main/packages/info_pl/assets/screenshots/Screenshot_20240325_234120.png"/></a>
   <a><img width="30%" src="https://raw.githubusercontent.com/fadimanakilci/pl_plugins/main/packages/info_pl/assets/screenshots/Screenshot_20240325_234144.png"/></a>
   <br/><br/>
  <span>Screenshots of the info text generator used</span>
</p>

### Individual Access to Data via DeviceInfo Example

Furthermore, individual access to the data obtained through this method is also possible via the `DeviceInfo` class.

```dart
import 'package:info_pl/info_pl.dart';

static final InfoPl _infoPl = InfoPl();
DeviceInfo? _deviceInfo;

Future<void> _getDeviceInfo() async {
  String? deviceBrandAndModel;
  
  try {
    _deviceInfo = await _infoPl.init();
    deviceBrandAndModel = '${_deviceInfo!.brand} ${_deviceInfo!.model}';
  } on PlatformException {
    deviceBrandAndModel = 'Failed to get device info.';
  }
}
```

### Generated UUID Example

A distinctive `UUID` created with `ID`, `android_ID` and `board` data is provided. This created `UUID` is accessed from the `DeviceInfo` class, like other data obtained.

```dart
import 'package:info_pl/info_pl.dart';

static final InfoPl _infoPl = InfoPl();
DeviceInfo? _deviceInfo = await _infoPl.init();
String? uuId = _deviceInfo!.uuId;
```

---

See the example app for more complex examples.
