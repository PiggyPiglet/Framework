# Reflection Based Project Framework
RPF, as the name suggests, is a reflection based Java project framework. What is reflection though? In essence, reflection is basically a tool that allows you to view and extend a class's architecture, for example, allowing you to get all annotations in a class, or on a method. RPF takes this basic tool to the next level though, by integrating well-known reflection utilizing libraries such as Guice and org.reflections, resulting in a unique and efficient way of creating applications.
### Features:
- Child injector based startup system
- Platform independent command API
- Task manager
- Manager API (including levenshtein searching)
- Configuration system (JSON Default)
- File manager
- Plenty of addons
  - MySQL API
  - Logback Implementation
  - HotswapAgent support (WIP)
  - Serve HTTP JSON
  - Console interaction
- Loads of pre-made bindings for popular apis
  - Bukkit
  - Sponge
  - JDA
  - Velocity (WIP)
  - Bungee (Soon)
  - Nukkit (Soon)
- More to come

Alone with these, there are also implementations for popular apis, such as Bukkit and JDA. In these, you can expect things that you'd normally have to do manually, to be done automatically, for example event registering.

### Usage
Comprehensive documentation can be found on the [wiki](https://github.com/PiggyPiglet/Framework/wiki), and method/class descriptions on the [javadocs](https://rpf.piggypiglet.me/docs).

Builds can be downloaded from [here](https://ci.piggypiglet.me/Framework), however I recommend you use my maven repository instead.<br/>
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
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-core?label=core)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-bukkit?label=bukkit)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-hotswap?label=hotswap)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-jda?label=jda)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-logback?label=logback)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-maven?label=maven)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/repo.piggypiglet.me/me.piggypiglet/framework-mysql?label=mysql)