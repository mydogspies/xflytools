
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/mydogspies/xflytools) ![GitHub last commit](https://img.shields.io/github/last-commit/mydogspies/xflytools) ![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/mydogspies/xflytools)
## xflytools

Xflytools is a remote application to set the autopilot, radios frequencies/transponder and exterior lights for Xplane default aircraft. The app is meant to ease the workload during critical parts of the flight when it's more efficient to gather all functions in one window/screen rather than paning around the cockpit with the mouse.

NOTE! Xflytools uses the excellent ExtPlane plugin from Ville Franki to interface with Xplane and will not work without. You can find this plugin on  [github]. Note that the version tested with the current stable version is already bundled with the binaries. No other versions are guaranteed to work with Xflytools.

This project is licensed under the terms of the GNU General Public License v3.0.

#### Version history:
Can be found in the [CHANGELOG] file. Latest stable version is always the [MASTER] branch. Alpha versions are not stable and not meant for aynthing else but testing out features. Use at our won risk.

#### Compatibility
Current version has been tested with Windows 10 only. An Android version is currently development but with no release date set yet.

Tested ONLY with Xplane 11.41 but there should be no issue with lower version provided ExtPlane works with them as well! Can NOT guarantee anything works with 11.5+ for now.

#### Current dependencies:
Xflytools is written in Java 8. All dependencies can be found in the pom.xml file. The installer comes bundled with the JRE so no need for any other installs beside this one. Xflytools uses the [ExtPlane] plugin, version 1003, for communication with Xplane. No other version is guaranteed to work until tested with.

[CHANGELOG]: https://github.com/mydogspies/xflytools/blob/master/CHANGELOG.md
[MASTER]: https://github.com/mydogspies/xflytools
[github]:https://github.com/vranki/ExtPlane/releases/
[ExtPlane]:https://github.com/vranki/ExtPlane/releases/