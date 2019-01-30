# Description
Standalone Google suggest API java client.  
It starts to query the Google suggest API with an initial user supplied term, then continues to do so devising the next query based on the last answer.

# Usage
You can invoke it using the Maven exec plugin:  
`mvn clean test exec:java -Dexec.mainClass="lc.gs.GoogleSuggestJaxRsXClient" -Dexec.args="%classpath" -Dexec.args="<initial query>"`

Exemple output:  
```
Querying for 'trump'
Google suggest response: ["trump",["trump","trump twitter","trump tower","trumpet","trump hotel","trump donald","trump stiri","trumpets","trump iran","trumpeter models"]]
Querying for 'trump twitter'
Google suggest response: ["trump twitter",["trump twitter","trump twitter macron","trump twitter nancy","trump twitter post","trump twitter political ad","trump twitter nike","trump twitter make france great again","trump twitter comey","trump twitter fire","trump twitter for iphone"]]
Querying for 'trump twitter macron'
Google suggest response: ["trump twitter macron",["trump twitter macron","trump tweet macron","trump macron twitter spat","twitter trump macron handshake","trump twitter against macron","tweet trump macron gilets jaunes","trump vs macron twitter","twitter trump sur macron","trump trudeau macron twitter","trump attacks macron twitter"]]
Querying for 'twitter macron'
Google suggest response: ["twitter macron",["twitter macron","twitter macron strasbourg","twitter macron brigitte","twitter macron trump","twitter macron gilet noir","twitter macron mister v","twitter macron bfm","macron 20h twitter","twitter macron gilets jaunes","twitter macron sommet"]]
Querying for 'twitter macron strasbourg'
Google suggest response: ["twitter macron strasbourg",["twitter macron strasbourg","twitter emmanuel macron strasbourg"]]
Querying for 'macron strasbourg'
Google suggest response: ["macron strasbourg",["macron strasbourg","macron strasbourg discours","macron strasbourg speech","macron strasbourg twitter","macron strasbourg 4 novembre","macron strasbourg direct","macron strasbourg programme","macron strasbourg tram","macron strasbourg 17 avril","macron strasbourg cathedrale"]]
Querying for 'macron strasbourg discours'
Google suggest response: ["macron strasbourg discours",["macron strasbourg discours","emmanuel macron discours strasbourg","discours macron strasbourg texte"]]
Querying for 'strasbourg discours'
Google suggest response: ["strasbourg discours",["strasbourg discours","strasbourg discours petite fille","discours strasbourg macron","churchill discours strasbourg","castaner discours strasbourg"]]
Querying for 'strasbourg discours petite fille'
Google suggest response: ["strasbourg discours petite fille",["strasbourg discours petite fille"]]
Querying for 'discours petite fille'
Google suggest response: ["discours petite fille",["discours petite fille onu","discours petite fille cop24","discours petite fille","discours petite fille climat","discours petite fille strasbourg","discours petite fille ecologie","discours petite fille suedoise","discours petite fille martin luther king","discours petite fille environnement","discours petite fille luther king"]]
Querying for 'discours petite fille onu'
Google suggest response: ["discours petite fille onu",["discours petite fille onu"]]
Querying for 'petite fille onu'
Google suggest response: ["petite fille onu",["discours petite fille onu"]]
Querying for 'fille onu'
Google suggest response: ["fille onu",["discours jeune fille onu","discours petite fille onu","discours fille onu"]]
Querying for 'onu'
Google suggest response: ["onu",["onu","onur tuna","onuc traian","onuc","onur air","onu romania","onur saylak","onu trans","onu referat","onufrie vinteler"]]
```
# Known Issues
1. Google Suggest API occasionally sends a response that does not respect the format of a valid response. The program fails to parse it and exits.
