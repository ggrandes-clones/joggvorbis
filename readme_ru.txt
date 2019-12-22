Java версия библиотек для работы с Ogg Vorbis.
*******
Соответствие:

libogg-1.3.3
libvorbis-1.3.6
+ тесты
+ java spi интерфейс
+ примеры.

*******
Не очень понятно, какие вызовы должны быть доступны снаружи,
так что может потребоваться изменить видимость некоторых методов.

Использовалась java версии 1.6.

Для уменьшения объёма и ускорения работы рекомендуется обработать обфускатором.
При обработке обфускатором следует сохранить имена классов внутри пакета spi.
Оптимизацию обфускатора следует применять осторожно, это может снизить скорость работы
и увеличить нагрузку на процессор.
*******

Метки в исходных файлах:
FIXME - подозрительные места в исходном коде на Си.
XXX - метки, отмечающие отладочный код и комментарии.
TODO - java-код, возможно, требующий дополнительной проработки.

*******
Смотри также:
Ogg, домашняя страница
http://www.xiph.org/ogg/
Vorbis, домашняя страница
http://www.xiph.org/vorbis/
Библиотеки для работы с OggVorbis, в том числе vorbis-java-1.0.0-beta на основе libvorbis-1.1.2, libogg-1.1.3
http://www.xiph.org/downloads/
JOrbis, a pure Java(TM) Ogg Vorbis decoder
http://www.jcraft.com/jorbis/
Vorbis SPI
http://www.javazoom.net/vorbisspi/vorbisspi.html
Ogg Vorbis Encoder aoTuV
http://www.geocities.jp/aoyoume/aotuv/
Ogg Vorbis acceleration project
http://homepage3.nifty.com/blacksword/index_e.htm
Vorbis RTP
http://www.j-ogg.de/rtp/index.html