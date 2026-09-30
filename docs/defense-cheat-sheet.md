# Что делает проект

Это учебная программа про воспроизведение музыки. Пользователь может выбрать тип плеера: обычный или для вечеринки. Обычный передаёт громкость 50, а плеер для вечеринки — 90. Ещё пользователь выбирает движок: локальный, облачный или старый. Программа получает эти значения из аргументов запуска; без аргументов использует local, basic и song.mp3. Движок выбирает фабрика, после чего плеер вызывает у него общий метод play. Настоящий звук программа не воспроизводит: она печатает сообщение, а старый движок дополнительно проверяет существование файла и расширение .mp3.

# Классы

- **MusicPlayer** — абстрактный родитель для плееров. Хранит ссылку только на AudioEngine и объявляет play(String filename). Его конструктор вызывают BasicPlayer и PartyPlayer через super.
- **BasicPlayer** — обычный плеер. В своём play вызывает audioEngine.play(filename, 50). Его создаёт Main.
- **PartyPlayer** — плеер для вечеринки. В своём play вызывает audioEngine.play(filename, 90). Его создаёт Main.
- **AudioEngine** — интерфейс, общий договор для движков. Метод play(String filename, int volume) может сообщить об ошибке через AudioException. Его вызывают оба плеера.
- **LocalAudioEngine** — обычная реализация AudioEngine. Метод play печатает сообщение о локальном воспроизведении. Фабрика создаёт объект по строке local.
- **CloudAudioEngine** — ещё одна обычная реализация AudioEngine. Метод play печатает сообщение о потоковом воспроизведении. Фабрика создаёт объект по строке cloud.
- **LegacyAudioSystem** — имитация старого API, который нельзя подгонять под новый интерфейс. Метод startTrack(int legacyVolume, String filePath) возвращает код: успех, файл не найден, устройство занято или формат не подходит. Его вызывает только адаптер.
- **LegacyAudioAdapter** — реализация AudioEngine вокруг LegacyAudioSystem. В play меняет порядок аргументов для startTrack и превращает коды ошибок в AudioException. Фабрика создаёт его для строки legacy, а плеер вызывает его как обычный AudioEngine.
- **AudioException** — общий тип ошибки воспроизведения. Его создаёт адаптер при ошибке и могут получать методы плееров; Main ловит его и печатает сообщение.
- **AudioEngineFactory** — место выбора конкретного движка. Статический метод create(String engineType) возвращает AudioEngine по строке local, cloud или legacy. Его вызывает Main.
- **Main** — точка входа. Метод main читает до трёх аргументов, просит фабрику создать движок, создаёт BasicPlayer или PartyPlayer и вызывает play.

# Bridge за 30 секунд

«У меня MusicPlayer — абстракция, BasicPlayer и PartyPlayer — её варианты. AudioEngine — интерфейс второй стороны, у него три реализации. Каждый плеер знает только AudioEngine, поэтому варианты плеера и движка можно добавлять независимо.»

# Adapter за 30 секунд

«LegacyAudioSystem имеет другой метод, обратный порядок аргументов и возвращает коды вместо исключений. LegacyAudioAdapter реализует AudioEngine, вызывает старый метод с нужным порядком аргументов и переводит результат в общий договор. Для плеера старый движок выглядит как обычный AudioEngine.»

# Dynamic Implementor Selection за 30 секунд

«Main берёт название движка из первого аргумента, по умолчанию local. Он передаёт строку в AudioEngineFactory.create. Фабрика выбирает конкретный класс во время запуска, а Main получает ссылку типа AudioEngine.»

# Open/Closed Principle за 30 секунд

«Можно добавить новый класс плеера, не меняя существующие движки, и новый класс движка, не меняя существующие плееры. Например, новый плеер вызовет AudioEngine.play со своей громкостью. Для нового названия движка нужно добавить ветку в фабрику — это ограничение моей реализации.»

# Что происходит при запуске программы

Пример: java -cp target/classes music.Main cloud basic song.mp3

1. Main читает cloud, basic и song.mp3.
2. Main вызывает AudioEngineFactory.create("cloud").
3. Фабрика создаёт CloudAudioEngine и возвращает его как AudioEngine.
4. Main передаёт этот объект в конструктор BasicPlayer.
5. Main вызывает player.play("song.mp3").
6. BasicPlayer вызывает audioEngine.play("song.mp3", 50); CloudAudioEngine печатает сообщение.

Пример со старым API: Main → фабрика выбирает legacy → создаёт LegacyAudioAdapter с LegacyAudioSystem → Main передаёт адаптер в плеер → плеер вызывает play → адаптер вызывает startTrack(volume, filename). Код 0 означает успех; коды 1, 2, 3 или неизвестный код превращаются в AudioException. Для успешного примера legacy нужен существующий файл с именем .mp3.

# Вопросы преподавателя

1. **Где у тебя Bridge?** MusicPlayer связан с AudioEngine через поле интерфейсного типа; две иерархии разделены.
2. **Что является Abstraction?** Абстрактный класс MusicPlayer.
3. **Что является Implementor?** Интерфейс AudioEngine.
4. **Какие Refined Abstractions?** BasicPlayer и PartyPlayer.
5. **Какие Concrete Implementors?** LocalAudioEngine, CloudAudioEngine и LegacyAudioAdapter.
6. **Где Adapter?** Класс LegacyAudioAdapter.
7. **Что такое Adaptee?** Старый класс LegacyAudioSystem, который оборачивает адаптер.
8. **Зачем нужен LegacyAudioSystem?** Он показывает, как подключить старый несовместимый API к новому проекту.
9. **Почему нельзя использовать LegacyAudioSystem напрямую?** У него нет метода AudioEngine.play, а ошибки возвращаются кодами.
10. **Почему он genuinely incompatible?** Метод называется startTrack, параметры идут как int и String в другом порядке, а результат — код ошибки, не AudioException.
11. **Почему нельзя было просто изменить LegacyAudioSystem?** По условию считаем его старым внешним кодом; его существующий API менять нельзя.
12. **Что делает LegacyAudioAdapter?** Вызывает startTrack с переставленными параметрами и переводит результат в договор AudioEngine.
13. **Как Adapter переводит ошибки?** Коды 1, 2, 3 и неизвестный код становятся AudioException с понятным сообщением; неожиданное RuntimeException тоже оборачивается.
14. **Может ли legacy error code попасть в BasicPlayer?** Нет. BasicPlayer видит только вызов AudioEngine.play и возможный AudioException.
15. **Почему одного Adapter недостаточно?** Он решает совместимость старого API, но не разделяет типы плееров и движков.
16. **Почему одного Bridge недостаточно?** Он разделяет иерархии, но сам не меняет метод, порядок аргументов и коды старого API.
17. **Как выполнен Open/Closed Principle?** Новый класс на одной стороне Bridge не требует изменений существующих классов на другой стороне.
18. **Как добавить новый AudioEngine?** Создать класс implements AudioEngine с play; для выбора по строке добавить ветку в фабрику.
19. **Как добавить новый MusicPlayer?** Создать подкласс extends MusicPlayer, вызвать super(audioEngine) и реализовать play со своим поведением.
20. **Что такое Dynamic Implementor Selection?** Выбор конкретной реализации интерфейса во время запуска по входному значению.
21. **Где именно выбирается реализация во время runtime?** В AudioEngineFactory.create(engineType).
22. **Зачем нужен AudioEngineFactory?** Чтобы Main не содержал выбора между LocalAudioEngine, CloudAudioEngine и LegacyAudioAdapter.
23. **Что проверяют JUnit tests?** Громкость и передачу имени файла обоими плеерами, выбор фабрики, вызов старого метода и перевод всех его ошибок.
24. **Зачем используется Mockito?** Он создаёт поддельные AudioEngine и LegacyAudioSystem: можно проверить вызов и задать нужный код ошибки без реального файла.
25. **В чём limitation проекта?** Для нового имени движка придётся изменить AudioEngineFactory.
26. **Что если старый класс вернёт неизвестный код?** Адаптер выбросит AudioException с сообщением Unknown audio playback error.
27. **Почему MusicPlayer хранит AudioEngine, а не LocalAudioEngine?** Тогда один и тот же плеер работает с любым движком через общий интерфейс.
28. **Главное отличие Bridge от Adapter здесь?** Bridge разделяет варианты плеера и движка; Adapter приводит один старый API к интерфейсу движка.

# Java, которую я должен понимать

- **class** — описание объекта. Например, BasicPlayer задаёт поведение обычного плеера.
- **object** — созданный экземпляр класса. Фабрика возвращает объект CloudAudioEngine для строки cloud.
- **interface** — договор методов. AudioEngine требует play(String, int).
- **implements** — класс выполняет договор интерфейса. LocalAudioEngine implements AudioEngine.
- **extends** — наследование. BasicPlayer extends MusicPlayer.
- **constructor** — создаёт и настраивает объект. BasicPlayer(AudioEngine) передаёт движок в super.
- **private** — доступ только внутри класса. DEFAULT_VOLUME закрыт внутри BasicPlayer.
- **protected** — доступ внутри класса и подклассов. Поле audioEngine объявлено в MusicPlayer и доступно BasicPlayer.
- **public** — доступ снаружи класса. Main может вызвать public метод player.play.
- **final** — значение ссылки нельзя заменить после присваивания. Поле audioEngine в MusicPlayer — final.
- **@Override** — пометка, что метод переопределяет метод родителя или интерфейса. Она стоит над play в BasicPlayer.
- **exception** — объект ошибки. AudioException сообщает о проблеме воспроизведения.
- **throws** — объявление, что метод может передать ошибку вызывающему коду. Это есть у AudioEngine.play.
- **throw** — фактическое выбрасывание ошибки. Адаптер делает throw new AudioException(...).
- **dependency** — один класс пользуется другим. MusicPlayer зависит от AudioEngine, а не от конкретного LocalAudioEngine.
- **polymorphism** — одна ссылка интерфейсного типа может указывать на разные реализации. AudioEngine в Main может быть локальным, облачным или адаптером.
