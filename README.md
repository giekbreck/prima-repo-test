# prima-repo-test

> Mia prima repo: la uso come test per provare un po' di cose su Git e GitHub.

Questo è il mio README, e lo userò anche come **mini tutorial personale** per ricordarmi come fare operazioni importanti con Git.  
Ogni volta che imparo qualcosa di nuovo, lo aggiungo qui sotto 

---

##  Indice

- [ Contenuto della repo](#-contenuto-della-repo)
- [ Comandi Git che ho imparato](#️-comandi-git-che-ho-imparato)
  - [ Primo setup](#-primo-setup)
  - [ Clonare una repo](#-clonare-una-repo)
  - [ Ciclo modifica → commit → push](#-ciclo-modifica--commit--push)
  - [ Gestione dei branch](#-gestione-dei-branch)
  - [ Usare lo stash](#-usare-lo-stash)
  - [ Creare un tag e una release](#-creare-un-tag-e-una-release)
- [ To-do personali](#-to-do-personali)
- [ LICENSE (MIT)](#-license-mit)
- [ CHANGELOG](#-changelog)

---

##  Contenuto della repo

- `src/` – codice sorgente (es. file Scala)
- `docs/` – documentazione tecnica o schema dati
- `test/` – eventuali file di test
- `.gitignore` – per escludere file inutili
- `CHANGELOG.md` – cronologia delle modifiche
- `LICENSE` – licenza del progetto

---

##  Comandi Git che ho imparato

### Primo setup

```bash
git config --global user.name "Giacomo Danese"
git config --global user.email "giacomodanese7@gmail.com"
```

---

### Clonare una repo

```bash
git clone https://github.com/giekbreck/prima-repo-test.git
```

---

### Ciclo modifica → commit → push

```bash
git status
git add <file>
git commit -m "Messaggio"
git push origin main
```

---

### Errore: Push rifiutato perché il branch remoto è più aggiornato

Se quando fai `git push origin main` ottieni questo errore:
```bash
! [rejected] main -> main (fetch first)
error: failed to push some refs to ...
hint: Updates were rejected because the remote contains work that you do not have locally.
```

Questo significa che il branch su GitHub contiene dei commit che non sono ancora presenti nel tuo repository locale..

#### Consiglio: quando lavori su più dispositivi o anche via web, fai sempre un pull prima di un push:

```bash
git pull origin main --rebase
git push origin main
```

---

###  Gestione dei branch

```bash
git checkout -b nuovo-branch
git switch main
git merge altro-branch
```

---

### Usare lo stash

```bash
git stash
git stash list
git stash apply
git stash pop
```

---

### Creare un tag e una release

```bash
git tag -a v1.0 -m "Prima versione stabile"
git push origin v1.0
```

---


### Aggiornare release

-Vai nella sezione 'Releases' sulla tua pagina GitHub

-Clicca su “Draft a new release”

-Scegli il tag v1.1

-Titolo: Versione 1.1 – Notebook + README

-Corpo: copia ciò che hai scritto nel changelog

-Pubblica cliccando "Publish release"

---

## To-do personali

### Completati
- Creare struttura cartelle (src, docs, test)

- Spostare i file nel posto giusto

- Usare .gitignore

- Capire gli stash

- Creare un tag e una release

### In spospeso
- Aggiungere file reali

- Imparare i pull request tra fork

- Automatizzare alcune operazioni con script

---

## LICENSE (MIT)

MIT License

Copyright (c) 2025 Giacomo Danese

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

---

## CHANGELOG

#### v1.0 – 2025-07-31
Prima versione stabile del progetto

-Aggiunta struttura base (src, docs, test)

-Creato .gitignore

-Primo file Scala spostato in src/

#### v1.1 - 2025-08-01
-Aggiunto notebook "test_git.ipynb" nella cartella src/

-Aggiunta sezione "Errore push rifiutato" e "Aggiornare release" nel README

-Aggiornamento struttura e documentazione



-Creato tag v1.0 e release associata
