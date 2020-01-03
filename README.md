# Reflection Based Project Framework
RPF, as the name suggests, is a reflection based Java project framework. What is reflection though? In essence, reflection is basically a tool that allows you to view and extend a class's architecture, for example, allowing you to get all annotations in a class, or on a method. RPF takes this basic tool to the next level though, by integrating well-known reflection utilizing libraries such as Guice and Guava, resulting in a unique and efficient way of creating applications.
### Features:
- Child injector based startup system
- Platform independent command API
- Extensive configurability (boot order, values throughout several components)
- Task manager
- Classpath scanning
- Manager API (implementations for MySQL, Web, Levenshtein search)
- Intelligent Object Mapper (similarity based key/variable comparisons)
- Configuration system (JSON Default, supports object mapping)
- Language System
- File manager
- Plenty of addons
  - MySQL API
  - Logback Implementation
  - Serve HTTP JSON
  - Console interaction
  - Runtime Jar Loader
- Loads of pre-made bindings for popular apis (with platform independent common libraries)
  - Bukkit
  - Sponge
  - Velocity
  - BungeeCord
  - Nukkit
  - Fabric (Soon)
  - JDA
  - Discord4J (Soon)
  - Javacord (Soon)
- More to come

Alone with these, there are also implementations for popular apis, such as Bukkit and JDA. In these, you can expect things that you'd normally have to do manually, to be done automatically, for example event registering.

### Usage
Comprehensive documentation can be found on the [wiki](https://github.com/PiggyPiglet/Framework/wiki), and method/class descriptions on the [javadocs](https://rpf.piggypiglet.me/docs).

Builds can be downloaded from [here](https://ci.piggypiglet.me/job/Framework/), however I recommend you use my maven repository instead.<br/>
[Gradle](https://github.com/PiggyPiglet/Framework/wiki/Gradle-Setup):
```groovy
repositories {
    mavenCentral()
    maven {
        url = "https://repo.piggypiglet.me/repository/maven-releases/"
    }
}
```
[Maven](https://github.com/PiggyPiglet/Framework/wiki/Maven-Setup):
```xml
<repositories>
  <repository>
    <id>piggypiglet</id>
    <name>piggypiglet</name>
    <url>https://repo.piggypiglet.me/repository/maven-releases/</url>
  </repository>
</repositories>
```
More info can be found on their respective pages.

### Status
![Jenkins](https://img.shields.io/jenkins/build/https/ci.piggypiglet.me/Framework)
<br/>Versions:

| Core  | Console | HTTP  | Jars  | Logback | MySQL | Bukkit | BungeeCord | JDA   | Sponge | Velocity | Nukkit |
|-------|---------|-------|-------|---------|-------|--------|------------|-------|--------|----------|--------|
| 1.8.13 | 1.8.13   | 1.8.13 | 1.8.13 | 1.8.13   | 1.8.13 | 1.8.13  | 1.8.13      | 1.8.13 | 1.8.13  | 1.8.13    | 1.8.13  |

### Endorsements
![YourKit](https://www.yourkit.com/images/yklogo.png)

YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).