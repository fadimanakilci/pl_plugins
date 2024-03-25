import 'package:flutter_test/flutter_test.dart';
import 'package:info_pl/info_pl.dart';
import 'package:info_pl/src/business/services/info_pl_platform_interface.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockInfoPlPlatform
    with MockPlatformInterfaceMixin
    implements InfoPlPlatformInterface {

  @override
  Future<DeviceInfo?> init() => Future.value(null);
}

void main() {
  final InfoPlPlatformInterface initialPlatform = InfoPlPlatformInterface.instance;

  test('$InfoPlPlatformInterface is the default instance', () {
    expect(initialPlatform, isInstanceOf<InfoPlPlatformInterface>());
  });

  test('init', () async {
    InfoPl infoPlPlugin = InfoPl();
    MockInfoPlPlatform fakePlatform = MockInfoPlPlatform();
    InfoPlPlatformInterface.instance = fakePlatform;

    expect(await infoPlPlugin.init(), null);
  });
}
