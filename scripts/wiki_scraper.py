import requests
import os
from re import compile, sub
from bs4 import BeautifulSoup

"""
This is a simple script to automatically extract paragraphs from wikipedia articles.
Now it just gets articles from natural sciences contents page, but it can be modified
to get more articles from other content pages.

Don't forget to use the fileformatter.py script after this one to use notgrep

Scraping tutorial that I used: https://realpython.com/beautiful-soup-web-scraper-python/#what-is-web-scraping 
Awesome website to figure out and test regex: https://regexr.com/
"""


def scrape(url: str, article_name: str):
    page = requests.get(url)
    soup = BeautifulSoup(page.content, "html.parser")

    paragraphs = soup.find_all('p')
    paragraph_count = 0

    project_dir = os.path.dirname(__file__)
    articles_dir = os.path.join(project_dir, "wiki_articles")

    with open(articles_dir + "/" + article_name, "w") as f:
        for p in paragraphs:
            cleaned_paragraph = sub(r'\[\d+\]', '', p.text)
            f.write(cleaned_paragraph)
            f.write("\n")
            paragraph_count += 1
            if paragraph_count > 5:
                break


def get_wikipedia_contents():
    # TODO maybe even get more links like that with scraping
    URL = "https://en.wikipedia.org/wiki/Wikipedia:Contents/Natural_and_physical_sciences"
    wikipedia_base_url = "https://en.wikipedia.org"
    keywords_to_exclude = ["Index_of", "Glossary_of", "Outline_of", "List_of", "Lists_of"]

    science_contents_page = requests.get(URL)
    soup = BeautifulSoup(science_contents_page.content, "html.parser")
    science_articles = soup.find_all('div', class_="contentsPage__section")

    for article in science_articles:
        anchor = article.find_all('a', href=compile('/wiki/'))
        for ref in anchor:
            link = (ref['href'])
            if '#' not in link and ':' not in link and not any(keyword in link for keyword in keywords_to_exclude):
                wiki_link = wikipedia_base_url + link
                article_name = link.split('/')[-1]
                print(f'starting to scrape wiki link {wiki_link}...')
                scrape(wiki_link, article_name)


if __name__ == "__main__":
    get_wikipedia_contents()
