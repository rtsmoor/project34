Readme V1.2
Code repository voor het project de bank


Tips voor git:
altijd als je begint: git pull
daarna pas bestanden aanpassen/nieuwe bestanden maken
om een branch te maken: git branch -c <oldBranch> <newBranch> (bijv: git branch -c master testBranch)
om tussen een branch te 'switchen': git switch <branchName>
alternatief voor git branch kan zijn git checkout -b <branchname>
om een branch te mergen gebruik je: git merge <branchNaam> --no-ff
(--no-ff is zodat de commit messages niet verwijderd worden ofzo)
daarna git push om het naar github te zetten
(source: https://thenewstack.io/dont-mess-with-the-master-working-with-branches-in-git-and-github/)

als je bestanden hebt ge-edit of nieuwe hebt aangemaakt kun je git status gebruiken om te zien welke
bestanden worden gecommit als je git commit doet. 
Als je het bestand wilt toevoegen aan de volgende commit dan moet je git add <bestandsnaam> doen
als je alle bestanden die je wilt uploaden hebt ge-add, dan kun je git status weer gebruiken
dan doe je git commit -m "belangrijke info over deze commit"
als je klaar bent met committen dan kun je git push gebruiken om het op github te zetten.

Feature branch workflow:

als je aan een nieuwe feature begint, maak je een nieuwe branch: 
git checkout -b new-feature-branchnaam master

commands om nieuwe bestanden toe te voegen/bewerken:
git status
git add <some-file>
git commit

als je klaar bent of pauze gaat houden:
git push -u origin new-feature-branchnaam

Als je klaar bent met de feature:
maak pull request:
git push (zodat de meest recente versie erop staat voor de pull request)

pull request op github maken
als er iets moet worden aangepast dan kan je de pull request naar je local repository doen
en het aanpassen
als de pull request geaccepteerd wordt, dan gebruik je de volgende commands:
git checkout development (of master als je van dev-branch naar master pullt)
git pull
git pull origin new-feature-branchnaam
git push
