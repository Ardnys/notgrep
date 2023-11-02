import os


def change_file():
    project_dir = os.path.dirname(__file__)
    files_dir = os.path.join(project_dir, "wiki_articles")
    file_names = os.listdir(files_dir)

    with open('all_wiki_articles.txt', 'w') as big_file:
        for file in file_names:
            with open(os.path.join(files_dir, file), 'r') as f:
                contents = f.readlines()
                # print(f"file {file}\ncontents {contents}")
                big_file.write(file + "\n")
                big_file.write('####\n')

                contents_str = "".join(contents)
                big_file.write(contents_str + "\n")
                big_file.write('####\n')


if __name__ == '__main__':
    change_file()
