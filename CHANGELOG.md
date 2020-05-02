# Changelog

All notable changes to the xflytools project will be documented in this file.

The latest stable production branch will always be [MASTER] once we are out of alpha.

### [Unreleased]

- 3rd party aircraft profiles.
- Autopilot functionality.
- Automatic connection to Xplane on startup

### [0.3.0-alpha] - 2020-05-02

#### Added

- IP setting for Xplane in the GUI

#### Changed

- Changed the way swap buttons switch and now it seems all to work. No more issues from ExtPlane's side either.

#### Known Issues

- On aircraft like the Baron where there is more than one lighting switch of the same kind, Xflytools will toggle both in whatever state they are, also when one is off and the other on.
- No A/P have yet been implemented. Only placeholders present.

### [0.2.0-alpha] - 2020-04-30

#### Added

- Lighting buttons and radios/transponder works now. NOTE! There seems at the moment to be a problem with ExtPlane whereby the swapping frequencies between left and right radios does not work.

#### Changed

- Working actively on making the communication between Xflytools and ExtPlane more solid.

#### Known Issues

- No A/P have yet been implemented. Only placeholders present.
- IP still hardcoded in SocketConnect class.
- Swapping frequencies does not work properly.

### [0.1.0-alpha] - 2020-04-28

#### Added

- Working: Toggle buttons for exterior lighting
- Placeholder: Field and buttons for radios and a/p
- NOTE! IP address is hard coded in the SocketConnect class until next release.

#### Changed

- no changes; this is the first alpha release

[MASTER]: https://github.com/mydogspies/xflytools
[unreleased]: https://github.com/mydogspies/xflytools/tree/develop
[0.1.0-alpha]: https://github.com/mydogspies/xflytools/v0.1.0-alpha
[0.2.0-alpha]: https://github.com/mydogspies/xflytools/v0.2.0-alpha
