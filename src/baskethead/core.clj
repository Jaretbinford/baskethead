(ns baskethead.core
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))

(def username "")
(def password "")

;;Cumulative_player stats! ###########################################


(def resp
  (http/get "https://api.mysportsfeeds.com/v1.1/pull/nba/2017-2018-regular/cumulative_player_stats.json?"
            {:as :json
             :basic-auth [username password]}
            ))

(def parsed
  (-> resp :body json/read-str))

(use 'clojure.walk)

(def keyworded
  (keywordize-keys parsed))

(clojure.pprint/pprint keyworded)

;;investigating player stats

(keys (first (:playerstatsentry (:cumulativeplayerstats keyworded))))

(keys (:player (first (:playerstatsentry (:cumulativeplayerstats keyworded)))))
(keys (:team (first (:playerstatsentry (:cumulativeplayerstats keyworded)))))
(keys (:stats (first (:playerstatsentry (:cumulativeplayerstats keyworded)))))

;Team should be a ref type
;Make them an enum



(defn player-txdata
  "Process a single player map"
  [player]
  {

   :player/ID (:ID (:player player))
   :player/last-name (:LastName (:player player))
   :player/first-name (:FirstName (:player player))
   :player/JerseyNumber (:JerseyNumber (:player player))
   :player/Position (:Position (:player player))
   :player/team { :team/ID (:ID (:team player))
                 :team/City (:City (:team player))
                 :team/team-name (:Name (:team player))
                 :team/abbreviation (:Abbreviation (:team player))}
   :player/stats {:stats/GamesPlayed (:#text (:GamesPlayed (:stats player)))
                  :stats/Fg2PtAtt (:#text (:Fg2PtAtt (:stats player)))
                  :stats/Fg2PtMade (:#text (:Fg2PtMade (:stats player)))
                  :stats/Fg3PtAtt (:#text (:Fg3PtAtt (:stats player)))
                  :stats/Fg3PtMade (:#text (:Fg3PtMade (:stats player)))
                  :stats/Fg3PtPct (:#text (:Fg3PtPct (:stats player)))
                  :stats/FgPct (:#text (:FgPct (:stats player)))
                  :stats/FtAtt (:#text (:FtAtt (:stats player)))
                  :stats/FtMade (:#text (:FtMade (:stats player)))
                  :stats/FtPct (:#text (:FtPct (:stats player)))
                  :stats/Pts (:#text (:Pts (:stats player)))
                  :stats/PtsPerGame (:#text (:PtsPerGame (:stats player)))
                  :stats/Ast (:#text (:Ast (:stats player)))
                  :stats/AstPerGame (:#text (:AstPerGame (:stats player)))
                  :stats/Stl (:#text (:Stl (:stats player)))
                  :stats/StlPerGame (:#text (:StlPerGame (:stats player)))
                  :stats/Reb (:#text (:Reb (:stats player)))
                  :stats/RebPerGame (:#text (:RebPerGame (:stats player)))
                  :stats/Tov (:#text (:Tov (:stats player)))
                  :stats/TovPerGame (:#text (:TovPerGame (:stats player)))
                  :stats/Blk (:#text (:BlkPerGame (:stats player)))
                  :stats/BlkPerGame (:#text (:BlkPerGame (:stats player)))

                  :stats/Fouls (:#text (:Fouls (:stats player)))
                  :stats/FgAttPerGame (:#text (:FgAttPerGame (:stats player)))
                  :stats/PlusMinusPerGame (:#text (:PlusMinusPerGame (:stats player)))
                  :stats/FoulFlag2DrawnPerGame (:#text (:FoulFlag2DrawnPerGame (:stats player)))
                  :stats/FoulsDrawn (:#text (:FoulsDrawn (:stats player)))
                  :stats/FoulFlag2 (:#text (:FoulFlag2 (:stats player)))
                  :stats/Fg3PtAttPerGame (:#text (:Fg3PtAttPerGame (:stats player)))
                  :stats/FoulPersDrawnPerGame (:#text (:FoulPersDrawnPerGame (:stats player)))
                  :stats/PlusMinus (:#text (:PlusMinus (:stats player)))
                  :stats/DefRebPerGame (:#text (:DefRebPerGame (:stats player)))
                  :stats/GamesStarted (:#text (:GamesStarted (:stats player)))
                  :stats/Fg2PtMadePerGame (:#text (:Fg2PtMadePerGame (:stats player)))
                  :stats/FoulFlag2PerGame (:#text (:FoulFlag2PerGame (:stats player)))
                  :stats/FoulsDrawnPerGame (:#text (:FoulsDrawnPerGame (:stats player)))
                  :stats/FoulTechDrawn (:#text (:FoulTechDrawn (:stats player)))
                  :stats/DefReb (:#text (:DefReb (:stats player)))
                  :stats/FgMadePerGame (:#text (:FgMadePerGame (:stats player)))
                  :stats/OffReb (:#text (:OffReb (:stats player)))
                  :stats/FoulTech (:#text (:FoulTech (:stats player)))
                  :stats/FoulPersPerGame (:#text (:FoulPersPerGame (:stats player)))
                  :stats/Fg3PtMadePerGame (:#text (:Fg3PtMadePerGame (:stats player)))
                  :stats/FoulFlag2Drawn (:#text (:FoulFlag2Drawn (:stats player)))
                  :stats/Fg2PtPct (:#text (:Fg2PtPct (:stats player)))
                  :stats/FoulsPerGame (:#text (:FoulsPerGame (:stats player)))
                  :stats/Fg2PtAttPerGame (:#text (:Fg2PtAttPerGame (:stats player)))
                  :stats/FoulFlag1Drawn (:#text (:FoulFlag1Drawn (:stats player)))
                  :stats/FoulFlag1 (:#text (:FoulFlag1 (:stats player)))
                  :stats/FtAttPerGame (:#text (:FtAttPerGame (:stats player)))
                  :stats/MinSecondsPerGame (:#text (:MinSecondsPerGame (:stats player)))
                  :stats/FoultechPerGame (:#text (:FoulTechPerGame (:stats player)))
                  :stats/Ejections (:#text (:Ejections (:stats player)))
                  :stats/FtMadePerGame (:#text (:FtMadePerGame (:stats player)))
                  :stats/BlkAgainst (:#text (:BlkAgainst (:stats player)))
                  :stats/FoulPersDrawn (:#text (:FoulPersDrawn (:stats player)))


                  }
   }
  )

(use 'clojure.pprint)

(def names (map player-txdata (:playerstatsentry (:cumulativeplayerstats keyworded))))

(clojure.pprint/pprint names)

;;Full game schedule! ###########################################


(def resp
  (http/get "https://api.mysportsfeeds.com/v1.1/pull/nba/2017-2018-regular/full_game_schedule.json"
            {:as :json
             :basic-auth [username password]}
            ))

(def parsed
  (-> resp :body json/read-str))

(use 'clojure.walk)

(def keyworded
  (keywordize-keys parsed))

(clojure.pprint/pprint keyworded)


;;;;; Invesigating Full Game Schedule

(keys (:fullgameschedule keyworded))
(keys (first (:gameentry (:fullgameschedule keyworded))))
(keys (:awayTeam (first (:gameentry (:fullgameschedule keyworded)))))
(keys (:homeTeam (first (:gameentry (:fullgameschedule keyworded)))))

(:gameentry (:fullgameschedule keyworded))

(map #(get % :date) (:gameentry (:fullgameschedule keyworded)))

(:date (:gameentry (:fullgameschedule keyworded)))


;{:fullgameschedule
; {:lastUpdatedOn "2017-11-03 8:41:12 AM",
;  :gameentry
;                 [{:date "2017-10-17",
;                   :originalTime nil,
;                   :delayedOrPostponedReason nil,
;                   :time "8:00PM",
;                   :awayTeam
;                   {:ID "82", :City "Boston", :Name "Celtics", :Abbreviation "BOS"},
;                   :homeTeam
;                   {:ID "86",
;                    :City "Cleveland",
;                    :Name "Cavaliers",
;                    :Abbreviation "CLE"},
;                   :scheduleStatus "Normal",
;                   :id "42070",
;                   :location "Quicken Loans Arena",
;                   :originalDate nil}


;;Defining schedule data

(defn schedule
  "process a single schedule map"
  [schedule]
  (:date (:gameentry schedule)))

(defn schedule-txdata
  "mapping schedule to schema"
  [schedule]
  {
   :game/id (:id schedule)
   :game/date (:date schedule)
   :game/time (:time schedule)
   :game/awayteamid (:ID (:awayTeam schedule))
   :game/awayteamname (:Name (:awayTeam schedule))
   :game/hometeamid (:ID (:homeTeam schedule))
   :game/hometeamname (:Name (:homeTeam schedule))
   :game/location (:location schedule)
   })

(use 'clojure.pprint)

(def names (map schedule-txdata (:gameentry (:fullgameschedule keyworded))))

(clojure.pprint/pprint names)
names



;;Daily Schedule! ###########################################


(def resp
  (http/get "https://api.mysportsfeeds.com/v1.1/pull/nba/2017-2018-regular/daily_game_schedule.json?fordate=20171021"
            {:as :json
             :basic-auth [username password]}
            ))

(def parsed
  (-> resp :body json/read-str))

(use 'clojure.walk)

(def keyworded
  (keywordize-keys parsed))

(clojure.pprint/pprint keyworded)

;; investigate daily game schedule
(keys (:dailygameschedule keyworded))
(keys (first (:gameentry (:dailygameschedule keyworded))))
(keys (:awayTeam (first (:gameentry (:dailygameschedule keyworded)))))
(keys (:homeTeam (first (:gameentry (:dailygameschedule keyworded)))))

(:gameentry (:dailygameschedule keyworded))


(defn daily-schedule-txdata
  "mapping schedule to schema"
  [schedule]
  {
   :game/id (:id schedule)
   :game/date (:date schedule)
   :game/time (:time schedule)
   :game/awayteamid (:ID (:awayTeam schedule))
   :game/awayteamname (:Name (:awayTeam schedule))
   :game/hometeamid (:ID (:homeTeam schedule))
   :game/hometeamname (:Name (:homeTeam schedule))
   :game/location (:location schedule)
   })

(use 'clojure.pprint)

(def names (map daily-schedule-txdata (:gameentry (:dailygameschedule keyworded))))

(clojure.pprint/pprint names)


;;Daily Schedule! ###########################################

;Keeping two separate databases


(def resp
  (http/get "https://api.mysportsfeeds.com/v1.1/pull/nba/2017-2018-regular/daily_player_stats.json?fordate=20171102"
            {:as :json
             :basic-auth [username password]}
            ))
(def parsed
  (-> resp :body json/read-str))

(use 'clojure.walk)

(def keyworded
  (keywordize-keys parsed))

(clojure.pprint/pprint keyworded)

(keys keyworded)

(keys (:dailyplayerstats keyworded))
(keys (first (:playerstatsentry (:dailyplayerstats keyworded))))


(keys (first (:playerstatsentry (:cumulativeplayerstats keyworded))))

(keys (:player (first (:playerstatsentry (:dailyplayerstats keyworded)))))
(keys (:team (first (:playerstatsentry (:dailyplayerstats keyworded)))))
(keys (:stats (first (:playerstatsentry (:dailyplayerstats keyworded)))))


(defn daily-player-txdata
  "Process a single player map"
  [player]
  {

   :player/ID (:ID (:player player))
   :player/last-name (:LastName (:player player))
   :player/first-name (:FirstName (:player player))
   :player/JerseyNumber (:JerseyNumber (:player player))
   :player/Position (:Position (:player player))
   :player/team { :team/ID (:ID (:team player))
                 :team/City (:City (:team player))
                 :team/team-name (:Name (:team player))
                 :team/abbreviation (:Abbreviation (:team player))}
   :player/stats {
                  :stats/Fg2PtAtt (:#text (:Fg2PtAtt (:stats player)))
                  :stats/Fg2PtMade (:#text (:Fg2PtMade (:stats player)))
                  :stats/Fg3PtAtt (:#text (:Fg3PtAtt (:stats player)))
                  :stats/Fg3PtMade (:#text (:Fg3PtMade (:stats player)))
                  :stats/Fg3PtPct (:#text (:Fg3PtPct (:stats player)))
                  :stats/FgPct (:#text (:FgPct (:stats player)))
                  :stats/FtAtt (:#text (:FtAtt (:stats player)))
                  :stats/FtMade (:#text (:FtMade (:stats player)))
                  :stats/FtPct (:#text (:FtPct (:stats player)))
                  :stats/Pts (:#text (:Pts (:stats player)))
                  :stats/PtsPerGame (:#text (:PtsPerGame (:stats player)))
                  :stats/Ast (:#text (:Ast (:stats player)))
                  :stats/AstPerGame (:#text (:AstPerGame (:stats player)))
                  :stats/Stl (:#text (:Stl (:stats player)))
                  :stats/StlPerGame (:#text (:StlPerGame (:stats player)))
                  :stats/Reb (:#text (:Reb (:stats player)))
                  :stats/RebPerGame (:#text (:RebPerGame (:stats player)))
                  :stats/Tov (:#text (:Tov (:stats player)))
                  :stats/TovPerGame (:#text (:TovPerGame (:stats player)))
                  :stats/Blk (:#text (:BlkPerGame (:stats player)))
                  :stats/BlkPerGame (:#text (:BlkPerGame (:stats player)))

                  :stats/Fouls (:#text (:Fouls (:stats player)))
                  :stats/FgAttPerGame (:#text (:FgAttPerGame (:stats player)))
                  :stats/PlusMinusPerGame (:#text (:PlusMinusPerGame (:stats player)))
                  :stats/FoulFlag2DrawnPerGame (:#text (:FoulFlag2DrawnPerGame (:stats player)))
                  :stats/FoulsDrawn (:#text (:FoulsDrawn (:stats player)))
                  :stats/FoulFlag2 (:#text (:FoulFlag2 (:stats player)))
                  :stats/Fg3PtAttPerGame (:#text (:Fg3PtAttPerGame (:stats player)))
                  :stats/FoulPersDrawnPerGame (:#text (:FoulPersDrawnPerGame (:stats player)))
                  :stats/PlusMinus (:#text (:PlusMinus (:stats player)))
                  :stats/DefRebPerGame (:#text (:DefRebPerGame (:stats player)))

                  :stats/Fg2PtMadePerGame (:#text (:Fg2PtMadePerGame (:stats player)))
                  :stats/FoulFlag2PerGame (:#text (:FoulFlag2PerGame (:stats player)))
                  :stats/FoulsDrawnPerGame (:#text (:FoulsDrawnPerGame (:stats player)))
                  :stats/FoulTechDrawn (:#text (:FoulTechDrawn (:stats player)))
                  :stats/DefReb (:#text (:DefReb (:stats player)))
                  :stats/FgMadePerGame (:#text (:FgMadePerGame (:stats player)))
                  :stats/OffReb (:#text (:OffReb (:stats player)))
                  :stats/FoulTech (:#text (:FoulTech (:stats player)))
                  :stats/FoulPersPerGame (:#text (:FoulPersPerGame (:stats player)))
                  :stats/Fg3PtMadePerGame (:#text (:Fg3PtMadePerGame (:stats player)))
                  :stats/FoulFlag2Drawn (:#text (:FoulFlag2Drawn (:stats player)))
                  :stats/Fg2PtPct (:#text (:Fg2PtPct (:stats player)))
                  :stats/FoulsPerGame (:#text (:FoulsPerGame (:stats player)))
                  :stats/Fg2PtAttPerGame (:#text (:Fg2PtAttPerGame (:stats player)))
                  :stats/FoulFlag1Drawn (:#text (:FoulFlag1Drawn (:stats player)))
                  :stats/FoulFlag1 (:#text (:FoulFlag1 (:stats player)))
                  :stats/FtAttPerGame (:#text (:FtAttPerGame (:stats player)))
                  :stats/MinSecondsPerGame (:#text (:MinSecondsPerGame (:stats player)))
                  :stats/FoultechPerGame (:#text (:FoulTechPerGame (:stats player)))
                  :stats/Ejections (:#text (:Ejections (:stats player)))
                  :stats/FtMadePerGame (:#text (:FtMadePerGame (:stats player)))
                  :stats/BlkAgainst (:#text (:BlkAgainst (:stats player)))
                  :stats/FoulPersDrawn (:#text (:FoulPersDrawn (:stats player)))


                  }
   }
  )

(use 'clojure.pprint)

(def names (map daily-player-txdata (:playerstatsentry (:dailyplayerstats keyworded))))

(clojure.pprint/pprint names)


;;Daily Schedule! ###########################################




(def resp
  (http/get "https://api.mysportsfeeds.com/v1.1/pull/nba/2017-2018-regular/game_boxscore.json?gameid=20171025-MIN-DET&teamstats=W,L,PTS,PTSA"
            {:as :json
             :basic-auth [username password]}
            ))
(def parsed
  (-> resp :body json/read-str))

(use 'clojure.walk)

(def keyworded
  (keywordize-keys parsed))

(clojure.pprint/pprint keyworded)

(keys keyworded)
(keys (:gameboxscore keyworded))

(keys (:game (:gameboxscore keyworded)))
(keys (:awayTeam (:gameboxscore keyworded)))
(keys (:awayTeamStats (:awayTeam (:gameboxscore keyworded))))
(keys (:awayPlayers (:awayTeam (:gameboxscore keyworded))))
(keys (:awayPlayers (:awayTeam (:gameboxscore keyworded))))
(keys (:homeTeam (:gameboxscore keyworded)))
(keys (:homeTeamStats (:homeTeam (:gameboxscore keyworded))))
(keys (:homePlayers (:homeTeam (:gameboxscore keyworded))))

(keys (:awayTeam (:game (:gameboxscore keyworded))))
(keys (:homeTeam (:game (:gameboxscore keyworded))))
(keys (:quarterSummary (:gameboxscore keyworded)))
(keys (:quarterTotals (:quarterSummary (:gameboxscore keyworded))))
(keys (:quarterTotals (:quarterSummary (:gameboxscore keyworded))))








;; Testing player search

(def playertest {:FirstName "Dillon"
                 :LastName "Brooks"
                 :JerseyNumber "24"
                 :team "Grizzlies"
                 :Position "SF"})


(defn print-player-info [{:keys [FirstName LastName JerseyNumber team Position]}]
  (println FirstName LastName "is the" Position "for" team ", wearing number" JerseyNumber))


(print-player-info playertest)





