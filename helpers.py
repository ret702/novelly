

def helplist():
    helplist="Commands:" +"\n" + \
              "login (username) (password) " + "\n" + \
              "push (repo) (commit message) (branch)" "\n" + \
              "staged (none)"
    print(hel ist)
    return {"result":"Error: Hint:Are you logged in?"}

def showstaged():
    fileList=""
    for filename in files:
        fileList+=filename +  " "
    if(fileList != None):
        print(fileList)
        return {"result":fileList}
    else:
        print(fileList)
        return {"result":"null"}
