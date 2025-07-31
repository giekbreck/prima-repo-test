# prima-repo-test

> Mia prima repo: la uso come test per provare un po' di cose su Git e GitHub.

Questo è il mio README, e lo userò anche come **mini tutorial personale** per ricordarmi come fare operazioni importanti con Git.  
Ogni volta che imparo qualcosa di nuovo, lo aggiungo qui sotto 👇

---

##  Contneuto della repo
- `src/` – codice sorgente (es. file Scala)
- `docs/` – documentazione tecnica o schema dati
- `test/` – eventuali file di test
- `.gitignore` – per escludere file inutili
- `CHANGELOG.md` – cronologia delle modifiche
- `LICENSE` – licenza del progetto

---

##  Comandi Git che ho imparato

###  Primo setup
```bash
git config --global user.name "Giacomo Danese"
git config --global user.email "giacomodanese7@gmail.com"


## Clonare una repo
git clone https://github.com/giekbreck/prima-repo-test.git


## Ciclo modifica → commit → push
git status
git add <file>
git commit -m "Messaggio"
git push origin main


## Branching
git checkout -b nuovo-branch
git switch main
git merge altro-branch


## Stash
git stash
git stash list
git stash apply

## Tag e Release
git tag -a v1.0 -m "Prima versione stabile"
git push origin v1.0


## To-do personali
### Completati
 -Creare struttura cartelle (src, docs, test)

 -Spostare i file nel posto giusto

 -Usare .gitignore

 -Capire gli stash

 -Creare un tag e una release

### To-do
 Aggiungere file reali

 Imparare i pull request tra fork

 Automatizzare alcune operazioni con script

