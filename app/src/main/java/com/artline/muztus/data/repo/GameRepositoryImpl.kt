package com.artline.muztus.data.repo

import com.artline.muztus.data.SharedPreferencesStorage
import com.muztus.core.levelsdata.premiaImagesList
import com.muztus.database.LevelInfoDAO
import com.muztus.database.LevelInfoEntity
import com.muztus.domain_layer.model.GameLevelModel
import com.muztus.domain_layer.model.GameSoundsInfo
import com.muztus.domain_layer.model.GameStatsInfo
import com.muztus.domain_layer.model.HintModel
import com.muztus.domain_layer.model.IGameSound
import com.muztus.domain_layer.model.LevelHints
import com.muztus.domain_layer.model.PremiaLevelModel
import com.muztus.domain_layer.repos.GameRepository
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(
    private val sharedPreferencesStorage: SharedPreferencesStorage,
    private val levelInfoDao: LevelInfoDAO
) : GameRepository {

    override fun getGameMainInfo(): GameStatsInfo = GameStatsInfo(
        coinsAmount = sharedPreferencesStorage.getCoinsAmount(),
        starsAmount = sharedPreferencesStorage.getStarsAmount(),
    )


    override val isFirstLaunch: Boolean
        get() = sharedPreferencesStorage.checkFirstLaunch()


    override fun setGameCoinsAmount(amount: Int, starsAmount: Int) {
        sharedPreferencesStorage.addCoins(amount)
        sharedPreferencesStorage.addStars(starsAmount)
    }

    override suspend fun getLevelInfo(premiumIndex: Int, levelIndex: Int): GameLevelModel {
        val levelEnt = levelInfoDao.findLevel(premiumIndex, levelIndex)
        val levelHints = levelEnt?.let { level ->
            LevelHints(
                letterAmountHint = HintModel.LetterAmountHint(isUsed = level.isLettersAmountUsed || levelEnt.isSolved),
                oneLetterHint = HintModel.OneLetterHint(
                    isUsed = level.selectedLetterIndex >= 0 || levelEnt.isSolved,
                    selectedLetters = level.selectedLetterIndex
                ),
                songHint = HintModel.SongHint(isUsed = level.isSongOpened || levelEnt.isSolved),
                correctAnswerHint = HintModel.CorrectAnswer(isUsed = level.isAnswerUsed || levelEnt.isSolved)
            )
        } ?: run {
            LevelHints()
        }


        return GameLevelModel.Base(
            index = levelIndex,
            premiumIndex = premiumIndex,
            correctAnswers = correctAnswersList[premiumIndex][levelIndex],
            levelHints = levelHints,
            levelImage = premiaImagesList[premiumIndex][levelIndex],
            songName = albumsList[premiumIndex][levelIndex],
            isSolved = levelEnt?.isSolved ?: false,
            levelEnt?.levelDuration ?: 0
        )
    }

    override suspend fun getDisksInfo(): Flow<List<LevelInfoEntity>> = levelInfoDao.getAllLevels()


    override suspend fun setLevelInfo(levelInfo: LevelInfoEntity) {
        levelInfoDao.insertOrUpdate(levelInfo)
    }

    override suspend fun getSelectedPremiumData(selectedPremiumIndex: Int): List<PremiaLevelModel> {
        val premiaLevels = levelInfoDao.getPremiaLevels(selectedPremiumIndex)
        return premiaImagesList[selectedPremiumIndex].mapIndexed { index, img ->
            PremiaLevelModel.Base(
                levelIndex = index,
                isLevelPassed = premiaLevels.find { it.levelIndex == index }?.isSolved ?: false,
                levelImage = img,
            )
        }
    }

    override suspend fun resetStatistic() {
        sharedPreferencesStorage.clearStarts()
        levelInfoDao.clearAllLevels()
    }

    override fun getSoundsState(): GameSoundsInfo =
        GameSoundsInfo(
            soundState = IGameSound.GameSound.apply {
                setSound(sharedPreferencesStorage.getSoundState())
            },
            musicState = IGameSound.GameMusic.apply {
                setSound(sharedPreferencesStorage.getMusicState())
            }
        )

    override fun setSoundsState(soundState: GameSoundsInfo) {
        sharedPreferencesStorage.setSoundsState(soundState)
    }


    companion object {
        private val correctAnswersList = listOf(
            listOf(listOf("мумий тролль")),
            listOf(
                listOf("пугачева", "алла пугачева", "алла борисовна", "алла"),
                listOf(
                    "тимати",
                    "тимур юнусов",
                    "Timati",
                    "тимур сказка",
                    "тимур беноевский",
                    "теймураз"
                ),
                listOf("би 2", "би два", "би-2", "би два"),
                listOf("стас михайлов", "михайлов", "станислав михайлов", "михайлов станислав"),
                listOf(
                    "лепс",
                    "лепс григорий",
                    "григорий лепс",
                    "григорий лепсверидзе",
                    "плепс",
                    "лепсверидзе григорий"
                ),
                listOf(
                    "елка",
                    "елизавета иванцив",
                    "иванцив елизавета",
                    "иванцив лиза",
                    "лиза иванцив",
                    "иванцив"
                ),
                listOf(
                    "дима билан",
                    "дима белан",
                    "виктор билан",
                    "виктор белан",
                    "билан дима",
                    "белан дима",
                    "билан виктор",
                    "белан виктор",
                    "билан",
                    "белан"
                ),
                listOf("сергей шнуров", "шнур", "ленинград", "шнуров сергей", "шнуров"),
                listOf(
                    "градский",
                    "александр градский",
                    "градский александр",
                    "александр фрадкин",
                    "фрадкин александр",
                    "славяне",
                    "скоморохи"
                )
            ),
            listOf(
                listOf(
                    "пелагея",
                    "телегина",
                    "пелагея телегина",
                    "телегина пелагея",
                    "смирнова полина",
                    "полина смирнова"
                ),
                listOf("чай вдвоем"),
                listOf("нюша"),
                listOf("шуфутинский", "михаил шуфутинский", "шуфутинский михаил", "миша", "михаил"),
                listOf("машина времени", "макаревич", "андрей макаревич", "макаревич андрей"),
                listOf(
                    "филипп киркоров",
                    "киркоров филипп",
                    "филипп бедросович",
                    "бедросович филипп",
                    "филипп",
                    "бедросович",
                    "киркоров"
                ),
                listOf("максим"),
                listOf("каста"),
                listOf("глюкоза"),
                listOf("наргиз", "закирова", "наргиз закирова", "закирова наргиз"),
                listOf("тату"),
                listOf("кино", "Цой", "виктор Цой", "Цой виктор"),
                listOf("басков", "николай басков", "басков николай"),
                listOf("агутин", "леонид агутин", "агутин леонид"),
                listOf("гуф"),
                listOf("николаев", "игорь николаев", "николаев игорь"),
                listOf("боярский", "михаил боярский", "боярский михаил"),
                listOf("ваенга")
            ),
            listOf(
                listOf("высоцкий", "владимир высоцкий", "высоцкий владимир"),
                listOf("меладзе", "константин меладзе", "меладзе константин"),
                listOf("сплин", "васильев", "александр васильев", "васильев александр"),
                listOf("розенбаум"),
                listOf("баста", "вася", "василий", "наггано", "василий вакуленко", "вакуленко"),
                listOf("король и шут", "киш", "горшок", "князь", "князев", "горшенев"),
                listOf("сергей лазарев", "лазарев сергей", "лазарев"),
                listOf("децл", "кирилл толмацкий", "толмацкий кирилл", "толмацкий"),
                listOf("потап и настя", "настя и потап", "потап", "потап и настя каменских"),
                listOf("руки вверх", "жуков", "сергей жуков", "жуков сергей"),
                listOf("уматурман", "ума2рман"),
                listOf("виагра"),
                listOf("ария", "кипелов"),
                listOf("лолита", "милявская", "милявская лолита", "милявская"),
                listOf("антонов"),
                listOf("серега", "серёга"),
                listOf("газманов", "олег газманов", "газманов олег"),
                listOf("гребенщиков", "борис гребенщиков", "бг")
            ),
            listOf(
                listOf("иванушки"),
                listOf("ддт", "юрий шевчук", "шевчук юрий", "шевчук"),
                listOf("агата кристи", "самойловы", "самойлов", "братья самойловы"),
                listOf("витас"),
                listOf("зверев", "сергей зверев", "зверев сергей"),
                listOf("гурченко", "людмила гурченко", "гурченко людмила"),
                listOf("дискотека авария"),
                listOf("фадеев", "максим фадеев", "фадеев максим"),
                listOf("моисеев", "борис моисеев", "моисеев борис"),
                listOf("фриске", "жанна фриске", "фриске жанна"),
                listOf("кобзон", "иосиф кобзон", "кобзон иосиф"),
                listOf("эдита пьеха", "пьеха", "пьеха эдита"),
                listOf("любэ", "расторгуев", "николай расторгуев", "расторгуев николай"),
                listOf("земфира"),
                listOf("трофим", "сергей трофимов", "трофимов сергей"),
                listOf("сукачев", "гарик сукачев", "сукачев гарик", "неприкосаемые"),
                listOf("пенкин", "сергей пенкин", "пенкин сергей"),
                listOf("наив")
            ),
            listOf(
                listOf("леонтьев"),
                listOf("фактор 2", "фактор два", "фактор2"),
                listOf("варум", "анжелика варум", "варум анжелика"),
                listOf("витя ак", "витяак", "витя", "ак-47", "ак", "ак 47"),
                listOf("вера брежнева", "брежнева", "брежнева вера"),
                listOf("агузарова", "жанна агузарова", "агузарова жанна"),
                listOf("братья грим", "грим"),
                listOf("валерия", "валерия перфилова", "перфилова валерия"),
                listOf(
                    "королева",
                    "королёва",
                    "наташа королева",
                    "наташа королёва",
                    "наталия королева",
                    "наталья королева",
                    "наталья королёва",
                    "наталия королёва"
                ),
                listOf("пикник", "эдмунд шклярский", "шклярский", "шклярский эдмунд"),
                listOf("кадышева", "надежда кадышева", "кадышева надежда"),
                listOf("софия ротару", "ротару", "ротару софия"),
                listOf("авраам руссо", "руссо", "авраам", "руссо авраам"),
                listOf("рефлекс"),
                listOf("бабкина", "надежда бабкина", "бабкина надежда"),
                listOf("серов"),
                listOf("чайф"),
                listOf("распутина", "маша распутина", "распутина маша")
            ),
            listOf(
                listOf("сердючка", "верка сердючка", "сердючка верка"),
                listOf("quest pistols", "квестпистолс", "квест пистолс"),
                listOf("Hi-Fi", "хайфай", "хай фай", "хай-фай", "hi fi"),
                listOf("коррозия металла", "паук"),
                listOf("джигурда"),
                listOf("нана"),
                listOf("шура"),
                listOf("ласковый май", "юрий шатунов", "шатунов", "шатунов юра"),
                listOf("токарев", "вилли токарев", "токарев вилли")
            ),
            listOf(listOf(""))
        )

        private val albumsList = listOf(
            listOf("Альбом: Морская\nПесня: Владивосток 2000"),
            listOf(
                "Альбом: Да!\nПесня: Позови меня с собой",
                "Альбом: Black Star\nПесня: В Клубе",
                "Альбом: Би-2\nПесня: Полковнику никто\nне пишет",
                "Альбом: Берега мечты\nПесня: Всё для тебя",
                "Альбом: На струнах дождя...\nПесня: Рюмка водки на столе",
                "Альбом: Точки расставлены\nПесня: Прованс",
                "Альбом: Я ночной хулиган\nПесня: Я ночной хулиган",
                "Альбом: Для миллионов\nПесня: Менеджер",
                "Альбом: ЖИВьЁМ в России\nПесня: Как молоды мы были..."
            ),
            listOf(
                "Альбом: Девушкины песни\nПесня: Казак",
                "Альбом: Ласковая моя\nПесня: Ласковая моя",
                "Альбом: Объединение\nПесня: Наедине",
                "Альбом: Гуляй, душа\nПесня: Третье сентября",
                "Альбом: В круге света\nПесня: Герои вчерашних дней",
                "Альбом: Незнакомка\nПесня: Роза чайная",
                "Альбом: Трудный возраст\nПесня: Знаешь ли ты",
                "Альбом: Громче воды, выше травы\nПесня: Куда надо смотреть",
                "Альбом: Москва\nПесня: Снег идёт",
                "Альбом: Шум сердца\nПесня: Ты - моя нежность",
                "Альбом: 200 по встречной\nПесня: Я сошла с ума",
                "Альбом: 45\nПесня: Восьмиклассница",
                "Альбом: Посвящение\nПесня: Памяти Карузо",
                "Альбом: Босоногий мальчик\nПесня: Хоп Хэй Лала Лэй",
                "Альбом: Город до́рог\nПесня: Сплетни",
                "Альбом: Дельфин и русалка\nПесня: Такси, такси",
                "Альбом: Графский переулок\nПесня: Петербург моего одиночества",
                "Альбом: Клавиши\nПесня: Курю"
            ),
            listOf(
                "Альбом: Концерт в ДК Мир\nПесня: Песня о друге",
                "Альбом: Самба белого мотылька\nПесня: Самба белого мотылька",
                "Альбом: 25-й кадр\nПесня: Моё сердце",
                "Альбом: Мои дворы\nПесня: Вальс-бостон",
                "Альбом: 3\nПесня: Солнца не видно",
                "Альбом: Как в старой сказке \nПесня: Проклятый старый дом",
                "Альбом: The best\nПесня: В самое сердце",
                "Альбом: Кто? Ты\nПесня: Вечеринка ",
                "Альбом: Не пара\nПесня: Не пара ",
                "Альбом: Без тормозов\nПесня: Ну, где же вы девчонки?",
                "Альбом: В городе N\nПесня: Проститься",
                "Альбом: Попытка № 5\nПесня: Что же я наделала?",
                "Альбом: Крещение огнём\nПесня: Колизей",
                "Альбом: Фетиш\nПесня: Прощай, оружие!",
                "Альбом: Крыша дома твоего\nПесня: Крыша дома твоего",
                "Альбом: The best of...\nПесня: Чёрный бумер",
                "Альбом: Морячка\nПесня: Морячка",
                "Альбом: Беспечный русский бродяяга\nПесня: Стаканы"
            ),
            listOf(
                "Альбом: Твои письма\nПесня: Кукла",
                "Альбом: Актриса Весна\nПесня: Родина",
                "Альбом: Ураган\nПесня: Грязь",
                "Альбом: Философия чуда\nПесня: Опера 2",
                "Альбом: Ради тебя\nПесня: Алла",
                "Альбом: -\nПесня: 5 минут",
                "Альбом: Авария против!\nПесня: Пей Пиво!",
                "Альбом: Ножницы\nПесня: Танцы на стеклах",
                "Альбом: Праздник! Праздник!\nПесня: Голубая луна",
                "Альбом: Жанна\nПесня: Ла-ла-ла",
                "Альбом: Танго, Танго, Танго\nПесня: Счастье Мое",
                "Альбом: -\nПесня: Наш сосед",
                "Альбом: Песни о людях\nПесня: Там, за туманами",
                "Альбом: Прости меня моя любовь\nПесня: Хочешь",
                "Альбом: Ветер в голове\nПесня: Боже, какой пустяк",
                "Альбом: Ночной полёт\nПесня: Моя бабушка курит трубку",
                "Альбом: Мистер Икс\nПесня: Ария Мистера Икс",
                "Альбом: Форева\nПесня: Я не шучу"
            ),
            listOf(
                "Альбом: Муза\nПесня: Полет на дельтаплане",
                "Альбом: В нашем стиле\nПесня: Красавица",
                "Альбом: Ля-ля-фа\nПесня: Ля-ля-фа",
                "Альбом: МегаPolice\nПесня: Оля Лукина",
                "Альбом: Любовь спасёт мир\nПесня: Любовь спасёт мир",
                "Альбом: Bravo\nПесня: Кошки",
                "Альбом: Братья Грим\nПесня: Ресницы",
                "Альбом: Страна Любви\nПесня: Часики",
                "Альбом: Конфетти\nПесня: Маленькая страна",
                "Альбом: Королевство кривых\nПесня: У шамана три руки",
                "Альбом: Когда-нибудь\nПесня: Широка река",
                "Альбом: Я: твоя любовь!\nПесня: Я назову планету",
                "Альбом: Обручальная\nПесня: Обручальная",
                "Альбом: Пульс\nПесня: Танцы",
                "Альбом: Продлись, моё счастье\nПесня: Финская полька",
                "Альбом: Я люблю тебя до слёз\nПесня: Я люблю тебя до слёз",
                "Альбом: Время не ждёт\nПесня: Не доводи до предела",
                "Альбом: Городская Сумасшедшая\nПесня: Клава"
            ),
            listOf(
                "Альбом: Ха-Ра-Шо!\nПесня: Все будет хорошо",
                "Альбом: Для тебя\nПесня: Я устал",
                "Альбом: Первый контакт\nПесня: Ты прости",
                "Альбом: Орден Сатаны\nПесня: Фантом",
                "Альбом: Волчья кровь\nПесня: Огонь любви",
                "Альбом: Фаина\nПесня: Фаина",
                "Альбом: Благодарю. Второе дыхание\nПесня: Твори добро",
                "Альбом: Белые розы\nПесня: Седая ночь",
                "Альбом: Над гудзоном\nПесня: Рыбацкая"
            ),
            listOf("")
        )
    }
}

